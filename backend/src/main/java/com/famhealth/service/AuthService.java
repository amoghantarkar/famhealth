package com.famhealth.service;

import com.famhealth.dto.AuthDtos.*;
import com.famhealth.entity.*;
import com.famhealth.exception.ApiException;
import com.famhealth.repository.*;
import com.famhealth.security.JwtService;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserEntityRepository userRepository;
    private final AccountRepository accountRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthResponse signup(SignupRequest req){
        if (userRepository.findByEmail(req.email()).isPresent()) throw new ApiException("Email already registered");
        UserEntity user = userRepository.save(UserEntity.builder().email(req.email()).phone(req.phone()).fullName(req.fullName())
                .passwordHash(encoder.encode(req.password())).status("ACTIVE").build());
        Account account = accountRepository.save(Account.builder().ownerUser(user).name(req.accountName()).countryCode(req.countryCode()).createdAt(Instant.now()).build());
        return tokens(user, account.getId());
    }

    public AuthResponse login(LoginRequest req){
        UserEntity user = userRepository.findByEmail(req.email()).orElseThrow(() -> new ApiException("Invalid credentials"));
        if (!encoder.matches(req.password(), user.getPasswordHash())) throw new ApiException("Invalid credentials");
        Account account = accountRepository.findAll().stream().filter(a -> a.getOwnerUser().getId().equals(user.getId())).findFirst().orElseThrow();
        return tokens(user, account.getId());
    }

    public AuthResponse refresh(RefreshRequest req){
        RefreshToken rt = refreshTokenRepository.findByTokenAndRevokedFalse(req.refreshToken()).orElseThrow(() -> new ApiException("Invalid refresh token"));
        if (rt.getExpiresAt().isBefore(Instant.now())) throw new ApiException("Expired refresh token");
        Account account = accountRepository.findAll().stream().filter(a -> a.getOwnerUser().getId().equals(rt.getUser().getId())).findFirst().orElseThrow();
        return tokens(rt.getUser(), account.getId());
    }

    public void logout(RefreshRequest req){ refreshTokenRepository.findByTokenAndRevokedFalse(req.refreshToken()).ifPresent(t -> {t.setRevoked(true);refreshTokenRepository.save(t);}); }

    private AuthResponse tokens(UserEntity user, Long accountId){
        String access = jwtService.generateAccessToken(user.getId(), accountId);
        String refresh = UUID.randomUUID().toString();
        refreshTokenRepository.save(RefreshToken.builder().user(user).token(refresh).expiresAt(Instant.now().plus(7, ChronoUnit.DAYS)).revoked(false).build());
        return new AuthResponse(access, refresh, user.getId(), accountId);
    }
}
