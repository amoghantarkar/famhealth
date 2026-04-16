package com.famhealth.service;

import com.famhealth.dto.RecordDtos.*;
import com.famhealth.entity.*;
import com.famhealth.exception.ApiException;
import com.famhealth.integration.*;
import com.famhealth.repository.*;
import com.famhealth.util.RequestUser;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class RecordService {
    private final MedicalRecordRepository medicalRecordRepository;
    private final RecordFileRepository recordFileRepository;
    private final PersonProfileRepository profileRepository;
    private final UserEntityRepository userRepository;
    private final ExtractedObservationRepository observationRepository;
    private final StorageService storageService;
    private final OcrProvider ocrProvider;
    private final ExtractionProvider extractionProvider;

    public RecordUploadResponse upload(Long profileId, String recordType, String providerName, java.time.LocalDate recordDate, MultipartFile file, RequestUser user){
        PersonProfile profile = resolveProfile(profileId, user);
        if (!profile.getAccount().getId().equals(user.accountId())) throw new ApiException("Access denied");
        UserEntity uploader = userRepository.findById(user.userId()).orElseThrow();
        MedicalRecord record = medicalRecordRepository.save(MedicalRecord.builder().account(profile.getAccount()).profile(profile).uploadedByUser(uploader)
                .recordType(defaultIfBlank(recordType, "GENERAL"))
                .providerName(defaultIfBlank(providerName, "Unknown Provider"))
                .recordDate(recordDate == null ? java.time.LocalDate.now() : recordDate)
                .sourceType("UPLOAD").processingStatus("UPLOADED").createdAt(Instant.now()).build());
        String key = storageService.upload(file);
        recordFileRepository.save(RecordFile.builder().medicalRecord(record).storageKey(key).fileName(file.getOriginalFilename()).mimeType(file.getContentType())
                .fileSize(file.getSize()).uploadedAt(Instant.now()).checksum(String.valueOf(file.getSize())).build());
        return new RecordUploadResponse(record.getId(), record.getProcessingStatus());
    }

    private PersonProfile resolveProfile(Long profileId, RequestUser user) {
        if (profileId != null) {
            return profileRepository.findById(profileId).orElseThrow();
        }
        return profileRepository.findFirstByAccountIdAndDeletedFalseOrderByCreatedAtAsc(user.accountId())
                .orElseThrow(() -> new ApiException("No profile found for this account. Create a profile first."));
    }

    private String defaultIfBlank(String value, String fallback) {
        return value == null || value.isBlank() ? fallback : value;
    }

    public List<RecordResponse> list(RequestUser user){
        return medicalRecordRepository.findByAccountId(user.accountId()).stream().map(r -> new RecordResponse(r.getId(), r.getRecordType(), r.getProviderName(), r.getRecordDate(), r.getProcessingStatus())).toList();
    }

    public RecordResponse get(Long id, RequestUser user){
        MedicalRecord r = medicalRecordRepository.findById(id).orElseThrow();
        if (!r.getAccount().getId().equals(user.accountId())) throw new ApiException("Access denied");
        return new RecordResponse(r.getId(), r.getRecordType(), r.getProviderName(), r.getRecordDate(), r.getProcessingStatus());
    }

    public String fileUrl(Long recordId, RequestUser user){
        MedicalRecord r = medicalRecordRepository.findById(recordId).orElseThrow();
        if (!r.getAccount().getId().equals(user.accountId())) throw new ApiException("Access denied");
        RecordFile rf = recordFileRepository.findAll().stream().filter(f -> f.getMedicalRecord().getId().equals(recordId)).findFirst().orElseThrow();
        return storageService.signedDownloadUrl(rf.getStorageKey());
    }

    public void process(Long recordId, RequestUser user){
        MedicalRecord r = medicalRecordRepository.findById(recordId).orElseThrow();
        if (!r.getAccount().getId().equals(user.accountId())) throw new ApiException("Access denied");
        RecordFile rf = recordFileRepository.findAll().stream().filter(f -> f.getMedicalRecord().getId().equals(recordId)).findFirst().orElseThrow();
        String text = ocrProvider.extractText(rf.getStorageKey());
        var parsed = extractionProvider.extract(text, r.getRecordDate());
        parsed.forEach(p -> observationRepository.save(ExtractedObservation.builder().medicalRecord(r).profile(r.getProfile())
                .metricCode(p.metricCode()).metricName(p.metricName()).valueText(p.valueText()).valueNumeric(p.valueNumeric()).unit(p.unit())
                .referenceRange(p.referenceRange()).observedAt(p.observedAt()).extractionConfidence(p.extractionConfidence()).sourceType(p.sourceType())
                .sourcePage(p.sourcePage()).sourceSnippet(p.sourceSnippet()).reviewStatus("NEEDS_REVIEW").createdAt(Instant.now()).build()));
        r.setProcessingStatus("PROCESSED");
        medicalRecordRepository.save(r);
    }
}
