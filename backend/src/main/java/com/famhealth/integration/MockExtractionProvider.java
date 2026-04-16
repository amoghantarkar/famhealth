package com.famhealth.integration;

import com.famhealth.service.model.ParsedObservation;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;

@Service
public class MockExtractionProvider implements ExtractionProvider {
    @Override
    public List<ParsedObservation> extract(String text, LocalDate recordDate) {
        List<ParsedObservation> out = new ArrayList<>();
        parse(out, text, "HbA1c", "hba1c", "%", recordDate);
        parse(out, text, "Glucose", "glucose", "mg/dL", recordDate);
        parse(out, text, "Creatinine", "creatinine", "mg/dL", recordDate);
        parse(out, text, "Hemoglobin", "hemoglobin", "g/dL", recordDate);
        return out;
    }

    private void parse(List<ParsedObservation> out, String text, String label, String code, String unit, LocalDate date){
        Matcher m = Pattern.compile(label + "\\s*([0-9]+(?:\\.[0-9]+)?)", Pattern.CASE_INSENSITIVE).matcher(text);
        if (m.find()) {
            out.add(new ParsedObservation(code, label, m.group(1), new BigDecimal(m.group(1)), unit, "", date,
                    new BigDecimal("0.81"), "DIRECT_EXTRACTION", 1, label + " extracted from report text"));
        }
    }
}
