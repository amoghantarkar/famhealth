package com.famhealth.controller;

import com.famhealth.dto.AuthDtos.*;
import com.famhealth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup") public AuthResponse signup(@Valid @RequestBody SignupRequest req){ return authService.signup(req); }
    @PostMapping("/login") public AuthResponse login(@Valid @RequestBody LoginRequest req){ return authService.login(req); }
    @PostMapping("/refresh") public AuthResponse refresh(@Valid @RequestBody RefreshRequest req){ return authService.refresh(req); }
    @PostMapping("/logout") public void logout(@Valid @RequestBody RefreshRequest req){ authService.logout(req); }
}
