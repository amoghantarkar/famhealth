package com.famhealth.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.*;

@Entity
@Table(name = "health_metric_summaries")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class HealthMetricSummary {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false) private PersonProfile profile;
    private String metricCode;
    private String metricName;
    private BigDecimal latestValue;
    private BigDecimal previousValue;
    private String trendDirection;
    private Integer recordCount;
    private Boolean abnormalFlag;
    private Instant lastUpdatedAt;
}
