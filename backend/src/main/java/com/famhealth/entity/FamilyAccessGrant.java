package com.famhealth.entity;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;

@Entity
@Table(name = "family_access_grants")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FamilyAccessGrant {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false) private Account account;
    @ManyToOne(optional = false) private PersonProfile profile;
    @ManyToOne(optional = false) @JoinColumn(name = "granted_to_user_id")
    private UserEntity grantedToUser;
    private String permissionLevel;
    private String status;
    private Instant grantedAt;
    private Instant revokedAt;
}
