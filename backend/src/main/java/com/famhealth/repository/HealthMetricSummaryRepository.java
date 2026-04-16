package com.famhealth.repository;

import com.famhealth.entity.HealthMetricSummary;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthMetricSummaryRepository extends JpaRepository<HealthMetricSummary, Long> {
}
