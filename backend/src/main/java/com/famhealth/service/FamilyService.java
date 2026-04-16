package com.famhealth.service;

import com.famhealth.dto.FamilyDtos.*;
import com.famhealth.entity.*;
import com.famhealth.exception.ApiException;
import com.famhealth.repository.*;
import com.famhealth.util.RequestUser;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FamilyService {
    private final FamilyAccessGrantRepository grantRepository;
    private final AccountRepository accountRepository;
    private final PersonProfileRepository profileRepository;
    private final UserEntityRepository userRepository;

    public FamilyAccessResponse share(ShareRequest req, RequestUser user){
        FamilyAccessGrant g = grantRepository.save(FamilyAccessGrant.builder().account(accountRepository.findById(user.accountId()).orElseThrow())
                .profile(profileRepository.findById(req.profileId()).orElseThrow()).grantedToUser(userRepository.findById(req.grantedToUserId()).orElseThrow())
                .permissionLevel(req.permissionLevel()).status("ACTIVE").grantedAt(Instant.now()).build());
        return new FamilyAccessResponse(g.getId(), g.getProfile().getId(), g.getGrantedToUser().getId(), g.getPermissionLevel(), g.getStatus());
    }

    public List<FamilyAccessResponse> list(RequestUser user){
        return grantRepository.findByAccountIdAndStatus(user.accountId(), "ACTIVE").stream()
                .map(g -> new FamilyAccessResponse(g.getId(), g.getProfile().getId(), g.getGrantedToUser().getId(), g.getPermissionLevel(), g.getStatus())).toList();
    }

    public void revoke(Long id, RequestUser user){
        FamilyAccessGrant g = grantRepository.findById(id).orElseThrow(() -> new ApiException("Share not found"));
        if (!g.getAccount().getId().equals(user.accountId())) throw new ApiException("Access denied");
        g.setStatus("REVOKED"); g.setRevokedAt(Instant.now()); grantRepository.save(g);
    }
}
