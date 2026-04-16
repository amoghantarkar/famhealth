package com.famhealth.service.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ParsedObservation(String metricCode, String metricName, String valueText, BigDecimal valueNumeric,
                                String unit, String referenceRange, LocalDate observedAt, BigDecimal extractionConfidence,
                                String sourceType, Integer sourcePage, String sourceSnippet) {}
