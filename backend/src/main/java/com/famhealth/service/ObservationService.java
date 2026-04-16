package com.famhealth.service;

import com.famhealth.dto.RecordDtos.*;
import com.famhealth.entity.*;
import com.famhealth.exception.ApiException;
import com.famhealth.repository.*;
import com.famhealth.util.RequestUser;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ObservationService {
    private final ExtractedObservationRepository observationRepository;
    private final ObservationReviewRepository reviewRepository;
    private final UserEntityRepository userRepository;

    public List<ObservationResponse> byProfile(Long profileId, RequestUser user){
        return observationRepository.findByProfileIdOrderByObservedAtDesc(profileId).stream().map(o ->
                new ObservationResponse(o.getId(), o.getMetricCode(), o.getMetricName(), o.getValueText(), o.getValueNumeric(), o.getUnit(), o.getReviewStatus(), o.getExtractionConfidence(),
                        "VERIFIED".equals(o.getReviewStatus()) ? "Verified by you" : "NEEDS_REVIEW".equals(o.getReviewStatus()) ? "Needs review" : "Extracted from report", o.getObservedAt())).toList();
    }

    public void review(Long id, ObservationReviewRequest req, RequestUser user){
        ExtractedObservation o = observationRepository.findById(id).orElseThrow();
        String original = o.getValueText();
        if (req.correctedValue() != null && !req.correctedValue().isBlank()) {
            o.setValueText(req.correctedValue());
            try { o.setValueNumeric(new BigDecimal(req.correctedValue())); } catch (Exception ignored) {}
        }
        o.setReviewStatus(req.reviewAction() == null ? "VERIFIED" : req.reviewAction());
        observationRepository.save(o);
        reviewRepository.save(ObservationReview.builder().observation(o).reviewedByUser(userRepository.findById(user.userId()).orElseThrow())
                .originalValue(original).correctedValue(req.correctedValue()).reviewAction(o.getReviewStatus()).reviewedAt(Instant.now()).build());
    }

    public DashboardResponse dashboard(Long profileId, RequestUser user){
        List<ExtractedObservation> obs = observationRepository.findByProfileIdOrderByObservedAtDesc(profileId);
        Map<String, List<ExtractedObservation>> byMetric = new LinkedHashMap<>();
        obs.forEach(o -> byMetric.computeIfAbsent(o.getMetricCode(), k -> new ArrayList<>()).add(o));
        List<DashboardMetric> metrics = byMetric.values().stream().map(list -> {
            list.sort(Comparator.comparing(ExtractedObservation::getObservedAt).reversed());
            ExtractedObservation latest = list.get(0);
            BigDecimal prev = list.size() > 1 ? list.get(1).getValueNumeric() : null;
            String trend = prev == null || latest.getValueNumeric() == null ? "STABLE" : latest.getValueNumeric().compareTo(prev) > 0 ? "UP" : latest.getValueNumeric().compareTo(prev) < 0 ? "DOWN" : "STABLE";
            boolean abnormal = latest.getMetricCode().equals("glucose") && latest.getValueNumeric() != null && latest.getValueNumeric().compareTo(new BigDecimal("125")) > 0;
            return new DashboardMetric(latest.getMetricCode(), latest.getMetricName(), latest.getValueNumeric(), prev, trend, list.size(), abnormal, latest.getObservedAt());
        }).toList();
        return new DashboardResponse(profileId, metrics, "Informational only. Not medical advice. Please consult a qualified doctor.");
    }
}
