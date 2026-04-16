package com.famhealth.integration;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MockChatAssistantProvider implements ChatAssistantProvider {
    @Override
    public String groundedResponse(String question, List<String> contextLines) {
        return "Based on the uploaded records, here are relevant values: " + String.join("; ", contextLines)
                + ". This is informational only, not medical advice. Please confirm with a doctor.";
    }
}
