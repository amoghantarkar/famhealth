package com.famhealth.repository;

import com.famhealth.entity.RecordFile;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordFileRepository extends JpaRepository<RecordFile, Long> {
}
