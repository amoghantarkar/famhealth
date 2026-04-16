package com.famhealth.entity;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;

@Entity
@Table(name = "record_files")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RecordFile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false) private MedicalRecord medicalRecord;
    private String storageKey;
    private String fileName;
    private String mimeType;
    private Long fileSize;
    private String checksum;
    private Instant uploadedAt;
}
