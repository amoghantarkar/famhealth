package com.famhealth.repository;

import com.famhealth.entity.PersonProfile;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonProfileRepository extends JpaRepository<PersonProfile, Long> {
    List<PersonProfile> findByAccountIdAndDeletedFalse(Long accountId);
    Optional<PersonProfile> findFirstByAccountIdAndDeletedFalseOrderByCreatedAtAsc(Long accountId);
}
