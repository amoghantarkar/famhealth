package com.famhealth.entity;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;

@Entity
@Table(name = "observation_reviews")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ObservationReview {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false) private ExtractedObservation observation;
    @ManyToOne(optional = false) @JoinColumn(name = "reviewed_by_user_id")
    private UserEntity reviewedByUser;
    private String originalValue;
    private String correctedValue;
    private String reviewAction;
    private Instant reviewedAt;
}
