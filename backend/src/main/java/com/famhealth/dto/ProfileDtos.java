package com.famhealth.dto;

import jakarta.validation.constraints.NotBlank;

public class ProfileDtos {
    public record ProfileRequest(@NotBlank String firstName, String lastName, @NotBlank String relationType, String sex, Integer birthYear, String timezone) {}
    public record ProfileResponse(Long id, String firstName, String lastName, String relationType, String sex, Integer birthYear, String timezone) {}
}
