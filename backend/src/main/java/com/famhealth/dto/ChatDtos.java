package com.famhealth.dto;

import java.util.List;

public class ChatDtos {
    public record SessionRequest(Long profileId, String title) {}
    public record MessageRequest(String content) {}
    public record MessageResponse(Long id, String role, String content, String citationsJson) {}
    public record GroundedChatResponse(List<MessageResponse> messages, String disclaimer) {}
}
