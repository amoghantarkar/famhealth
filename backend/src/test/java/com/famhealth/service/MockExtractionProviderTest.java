package com.famhealth.service;

import static org.junit.jupiter.api.Assertions.*;

import com.famhealth.integration.MockExtractionProvider;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class MockExtractionProviderTest {
    @Test
    void parsesCommonLabs() {
        MockExtractionProvider provider = new MockExtractionProvider();
        var result = provider.extract("HbA1c 6.2 Glucose 101 Creatinine 0.9", LocalDate.parse("2026-04-01"));
        assertTrue(result.size() >= 3);
        assertEquals("hba1c", result.get(0).metricCode());
    }
}
