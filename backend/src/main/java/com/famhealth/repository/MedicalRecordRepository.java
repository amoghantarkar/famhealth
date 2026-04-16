package com.famhealth.repository;

import com.famhealth.entity.MedicalRecord;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    List<MedicalRecord> findByProfileIdOrderByRecordDateDesc(Long profileId);
    List<MedicalRecord> findByAccountId(Long accountId);
}
