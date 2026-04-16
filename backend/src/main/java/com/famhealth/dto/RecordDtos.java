package com.famhealth.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class RecordDtos {
    public record RecordUploadResponse(Long recordId, String processingStatus) {}
    public record RecordResponse(Long id, String recordType, String providerName, LocalDate recordDate, String processingStatus) {}
    public record ObservationResponse(Long id, String metricCode, String metricName, String valueText, BigDecimal valueNumeric,
                                      String unit, String reviewStatus, BigDecimal extractionConfidence, String sourceLabel, LocalDate observedAt) {}
    public record ObservationReviewRequest(String correctedValue, String reviewAction) {}
    public record DashboardMetric(String metricCode, String metricName, BigDecimal latestValue, BigDecimal previousValue, String trendDirection,
                                  Integer count, Boolean abnormalFlag, LocalDate lastUpdatedDate) {}
    public record DashboardResponse(Long profileId, List<DashboardMetric> metrics, String disclaimer) {}
}
