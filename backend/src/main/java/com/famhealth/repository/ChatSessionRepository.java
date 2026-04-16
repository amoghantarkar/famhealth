package com.famhealth.repository;

import com.famhealth.entity.ChatSession;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatSessionRepository extends JpaRepository<ChatSession, Long> {
    List<ChatSession> findByProfileId(Long profileId);
}
