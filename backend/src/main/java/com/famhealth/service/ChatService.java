package com.famhealth.service;

import com.famhealth.dto.ChatDtos.*;
import com.famhealth.entity.*;
import com.famhealth.integration.ChatAssistantProvider;
import com.famhealth.repository.*;
import com.famhealth.util.RequestUser;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatSessionRepository sessionRepository;
    private final ChatMessageRepository messageRepository;
    private final AccountRepository accountRepository;
    private final PersonProfileRepository profileRepository;
    private final UserEntityRepository userRepository;
    private final ExtractedObservationRepository observationRepository;
    private final ChatAssistantProvider chatAssistantProvider;

    public ChatSession create(SessionRequest req, RequestUser user){
        return sessionRepository.save(ChatSession.builder().account(accountRepository.findById(user.accountId()).orElseThrow())
                .profile(profileRepository.findById(req.profileId()).orElseThrow()).createdByUser(userRepository.findById(user.userId()).orElseThrow())
                .title(req.title() == null ? "Health Records Chat" : req.title()).createdAt(Instant.now()).build());
    }

    public List<ChatSession> sessions(Long profileId){ return sessionRepository.findByProfileId(profileId); }
    public List<MessageResponse> messages(Long sessionId){ return messageRepository.findByChatSessionIdOrderByCreatedAtAsc(sessionId).stream().map(m -> new MessageResponse(m.getId(), m.getRole(), m.getContent(), m.getCitationsJson())).toList(); }

    public GroundedChatResponse ask(Long sessionId, MessageRequest req, RequestUser user){
        ChatSession session = sessionRepository.findById(sessionId).orElseThrow();
        messageRepository.save(ChatMessage.builder().chatSession(session).role("user").content(req.content()).createdAt(Instant.now()).build());
        List<String> context = observationRepository.findByProfileIdOrderByObservedAtDesc(session.getProfile().getId()).stream().limit(5)
                .map(o -> o.getObservedAt() + " " + o.getMetricName() + "=" + o.getValueText() + " " + o.getUnit()).toList();
        String assistant = chatAssistantProvider.groundedResponse(req.content(), context);
        String citations = context.toString();
        ChatMessage saved = messageRepository.save(ChatMessage.builder().chatSession(session).role("assistant").content(assistant).citationsJson(citations).createdAt(Instant.now()).build());
        return new GroundedChatResponse(List.of(new MessageResponse(saved.getId(), saved.getRole(), saved.getContent(), saved.getCitationsJson())),
                "Informational only, not medical advice. Please consult a qualified doctor.");
    }
}
