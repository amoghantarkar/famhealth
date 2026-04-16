package com.famhealth.repository;

import com.famhealth.entity.RefreshToken;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByTokenAndRevokedFalse(String token);
}
