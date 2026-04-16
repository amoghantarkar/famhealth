package com.famhealth.repository;

import com.famhealth.entity.Account;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
