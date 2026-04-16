package com.famhealth.entity;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;

@Entity
@Table(name = "audit_logs")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AuditLog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name = "actor_user_id")
    private UserEntity actorUser;
    @ManyToOne private Account account;
    @ManyToOne private PersonProfile profile;
    private String actionType;
    private String resourceType;
    private String resourceId;
    @Column(length = 5000)
    private String metadataJson;
    private Instant createdAt;
}
