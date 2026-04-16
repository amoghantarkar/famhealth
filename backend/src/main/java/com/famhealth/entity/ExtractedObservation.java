package com.famhealth.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import lombok.*;

@Entity
@Table(name = "extracted_observations")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ExtractedObservation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false) private MedicalRecord medicalRecord;
    @ManyToOne(optional = false) private PersonProfile profile;
    private String metricCode;
    private String metricName;
    private String valueText;
    private BigDecimal valueNumeric;
    private String unit;
    private String referenceRange;
    private LocalDate observedAt;
    private BigDecimal extractionConfidence;
    private String sourceType;
    private Integer sourcePage;
    @Column(length = 3000)
    private String sourceSnippet;
    private String reviewStatus;
    private Instant createdAt;
}
