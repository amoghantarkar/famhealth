package com.famhealth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AuthDtos {
    public record SignupRequest(@Email String email, @NotBlank String password, @NotBlank String fullName, String phone,
                                @NotBlank String accountName, @NotBlank String countryCode) {}
    public record LoginRequest(@Email String email, @NotBlank String password) {}
    public record RefreshRequest(@NotBlank String refreshToken) {}
    public record AuthResponse(String accessToken, String refreshToken, Long userId, Long accountId) {}
}
