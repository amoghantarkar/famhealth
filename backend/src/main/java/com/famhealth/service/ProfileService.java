package com.famhealth.service;

import com.famhealth.dto.ProfileDtos.*;
import com.famhealth.entity.Account;
import com.famhealth.entity.PersonProfile;
import com.famhealth.exception.ApiException;
import com.famhealth.repository.AccountRepository;
import com.famhealth.repository.PersonProfileRepository;
import com.famhealth.util.RequestUser;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final PersonProfileRepository profileRepository;
    private final AccountRepository accountRepository;

    public List<ProfileResponse> list(RequestUser user){
        return profileRepository.findByAccountIdAndDeletedFalse(user.accountId()).stream().map(this::toResponse).toList();
    }
    public ProfileResponse get(Long id, RequestUser user){ return toResponse(requireOwned(id, user)); }
    public ProfileResponse create(ProfileRequest req, RequestUser user){
        Account account = accountRepository.findById(user.accountId()).orElseThrow();
        PersonProfile p = profileRepository.save(PersonProfile.builder().account(account).firstName(req.firstName()).lastName(req.lastName())
                .relationType(req.relationType()).sex(req.sex()).birthYear(req.birthYear()).timezone(req.timezone()).createdAt(Instant.now()).deleted(false).build());
        return toResponse(p);
    }
    public ProfileResponse update(Long id, ProfileRequest req, RequestUser user){
        PersonProfile p = requireOwned(id, user); p.setFirstName(req.firstName()); p.setLastName(req.lastName()); p.setRelationType(req.relationType());
        p.setSex(req.sex()); p.setBirthYear(req.birthYear()); p.setTimezone(req.timezone());
        return toResponse(profileRepository.save(p));
    }
    public void delete(Long id, RequestUser user){ PersonProfile p = requireOwned(id, user); p.setDeleted(true); profileRepository.save(p); }

    private PersonProfile requireOwned(Long id, RequestUser user){
        PersonProfile p = profileRepository.findById(id).orElseThrow(() -> new ApiException("Profile not found"));
        if (!p.getAccount().getId().equals(user.accountId())) throw new ApiException("Access denied");
        return p;
    }
    private ProfileResponse toResponse(PersonProfile p){ return new ProfileResponse(p.getId(), p.getFirstName(), p.getLastName(), p.getRelationType(), p.getSex(), p.getBirthYear(), p.getTimezone()); }
}
