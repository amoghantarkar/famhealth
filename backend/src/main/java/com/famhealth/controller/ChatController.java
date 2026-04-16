package com.famhealth.controller;

import com.famhealth.dto.ChatDtos.*;
import com.famhealth.entity.ChatSession;
import com.famhealth.service.ChatService;
import com.famhealth.util.RequestUserResolver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService service;
    private final RequestUserResolver resolver;

    @PostMapping("/sessions") public ChatSession create(@RequestBody SessionRequest req,@RequestHeader("X-User-Id") String uid,@RequestHeader("X-Account-Id") String aid){ return service.create(req, resolver.fromHeaders(uid, aid)); }
    @GetMapping("/sessions") public List<ChatSession> sessions(@RequestParam Long profileId){ return service.sessions(profileId); }
    @GetMapping("/sessions/{id}/messages") public List<MessageResponse> messages(@PathVariable Long id){ return service.messages(id); }
    @PostMapping("/sessions/{id}/messages") public GroundedChatResponse ask(@PathVariable Long id,@RequestBody MessageRequest req,@RequestHeader("X-User-Id") String uid,@RequestHeader("X-Account-Id") String aid){ return service.ask(id, req, resolver.fromHeaders(uid, aid)); }
}
