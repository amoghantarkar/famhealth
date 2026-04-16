package com.famhealth.integration;

import org.springframework.stereotype.Service;

@Service
public class MockOcrProvider implements OcrProvider {
    @Override
    public String extractText(String storageKey) {
        return "HbA1c 6.4 %, Glucose 108 mg/dL, Creatinine 1.0 mg/dL, Hemoglobin 13.4 g/dL";
    }
}
