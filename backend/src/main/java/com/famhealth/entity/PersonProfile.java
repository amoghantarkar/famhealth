package com.famhealth.entity;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;

@Entity
@Table(name = "person_profiles")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PersonProfile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false) @JoinColumn(name = "account_id")
    private Account account;
    private String firstName;
    private String lastName;
    private String relationType;
    private String sex;
    private Integer birthYear;
    private String timezone;
    private Instant createdAt;
    @Column(nullable = false)
    private boolean deleted;
}
