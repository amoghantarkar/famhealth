package com.famhealth.repository;

import com.famhealth.entity.UserEntity;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
}
