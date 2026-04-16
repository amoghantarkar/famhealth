package com.famhealth.entity;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;

@Entity
@Table(name = "chat_sessions")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ChatSession {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false) private Account account;
    @ManyToOne(optional = false) private PersonProfile profile;
    @ManyToOne(optional = false) @JoinColumn(name = "created_by_user_id")
    private UserEntity createdByUser;
    private String title;
    private Instant createdAt;
}
