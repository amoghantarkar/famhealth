package com.famhealth.service;

import com.famhealth.entity.UserEntity;
import com.famhealth.repository.UserEntityRepository;
import com.famhealth.util.RequestUser;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrivacyService {
    private final UserEntityRepository userRepository;

    public Map<String, String> privacy(){
        return Map.of("privacy", "Your records are accessible only to your account and explicit family shares.",
                "disclaimer", "Informational only. Not medical advice.");
    }

    public void deleteAccount(RequestUser user){
        UserEntity u = userRepository.findById(user.userId()).orElseThrow();
        u.setStatus("DELETED");
        u.setDeleted(true);
        userRepository.save(u);
    }
}
