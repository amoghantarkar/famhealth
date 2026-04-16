package com.famhealth.entity;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;

@Entity
@Table(name = "refresh_tokens")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RefreshToken {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private UserEntity user;
    @Column(unique = true, nullable = false)
    private String token;
    private Instant expiresAt;
    private boolean revoked;
}
