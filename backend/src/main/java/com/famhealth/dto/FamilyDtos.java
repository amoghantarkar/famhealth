package com.famhealth.dto;

public class FamilyDtos {
    public record ShareRequest(Long profileId, Long grantedToUserId, String permissionLevel) {}
    public record FamilyAccessResponse(Long id, Long profileId, Long grantedToUserId, String permissionLevel, String status) {}
}
