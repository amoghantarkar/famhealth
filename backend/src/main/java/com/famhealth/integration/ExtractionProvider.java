package com.famhealth.integration;

import com.famhealth.service.model.ParsedObservation;
import java.time.LocalDate;
import java.util.List;

public interface ExtractionProvider {
    List<ParsedObservation> extract(String text, LocalDate recordDate);
}
