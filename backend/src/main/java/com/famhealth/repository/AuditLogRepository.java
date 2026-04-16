package com.famhealth.repository;

import com.famhealth.entity.AuditLog;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
