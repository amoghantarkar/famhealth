package com.famhealth.repository;

import com.famhealth.entity.FamilyAccessGrant;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyAccessGrantRepository extends JpaRepository<FamilyAccessGrant, Long> {
    List<FamilyAccessGrant> findByAccountIdAndStatus(Long accountId, String status);
}
