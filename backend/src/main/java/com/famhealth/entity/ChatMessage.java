package com.famhealth.entity;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;

@Entity
@Table(name = "chat_messages")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ChatMessage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false) private ChatSession chatSession;
    private String role;
    @Column(length = 5000)
    private String content;
    @Column(length = 5000)
    private String citationsJson;
    private Instant createdAt;
}
