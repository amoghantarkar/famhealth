package com.famhealth.integration;

import java.util.List;

public interface ChatAssistantProvider {
    String groundedResponse(String question, List<String> contextLines);
}
