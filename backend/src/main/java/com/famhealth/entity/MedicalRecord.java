package com.famhealth.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import lombok.*;

@Entity
@Table(name = "medical_records")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MedicalRecord {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false) private Account account;
    @ManyToOne(optional = false) private PersonProfile profile;
    @ManyToOne(optional = false) @JoinColumn(name = "uploaded_by_user_id")
    private UserEntity uploadedByUser;
    private String recordType;
    private String providerName;
    private LocalDate recordDate;
    private String sourceType;
    private String processingStatus;
    private Instant createdAt;
}
