package com.famhealth.controller;

import com.famhealth.dto.ProfileDtos.*;
import com.famhealth.service.ProfileService;
import com.famhealth.util.RequestUserResolver;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService service;
    private final RequestUserResolver resolver;

    @GetMapping public List<ProfileResponse> list(@RequestHeader("X-User-Id") String uid, @RequestHeader("X-Account-Id") String aid){ return service.list(resolver.fromHeaders(uid, aid)); }
    @PostMapping public ProfileResponse create(@RequestHeader("X-User-Id") String uid,@RequestHeader("X-Account-Id") String aid,@Valid @RequestBody ProfileRequest req){ return service.create(req, resolver.fromHeaders(uid, aid)); }
    @GetMapping("/{id}") public ProfileResponse get(@PathVariable Long id,@RequestHeader("X-User-Id") String uid,@RequestHeader("X-Account-Id") String aid){ return service.get(id, resolver.fromHeaders(uid, aid)); }
    @PutMapping("/{id}") public ProfileResponse update(@PathVariable Long id,@RequestHeader("X-User-Id") String uid,@RequestHeader("X-Account-Id") String aid,@Valid @RequestBody ProfileRequest req){ return service.update(id, req, resolver.fromHeaders(uid, aid)); }
    @DeleteMapping("/{id}") public void delete(@PathVariable Long id,@RequestHeader("X-User-Id") String uid,@RequestHeader("X-Account-Id") String aid){ service.delete(id, resolver.fromHeaders(uid, aid)); }
}
