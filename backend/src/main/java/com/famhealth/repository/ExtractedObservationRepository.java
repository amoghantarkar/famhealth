package com.famhealth.repository;

import com.famhealth.entity.ExtractedObservation;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExtractedObservationRepository extends JpaRepository<ExtractedObservation, Long> {
    List<ExtractedObservation> findByProfileIdOrderByObservedAtDesc(Long profileId);
    List<ExtractedObservation> findByMedicalRecordId(Long medicalRecordId);
}
