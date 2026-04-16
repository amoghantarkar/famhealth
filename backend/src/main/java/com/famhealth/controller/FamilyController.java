package com.famhealth.controller;

import com.famhealth.dto.FamilyDtos.*;
import com.famhealth.service.FamilyService;
import com.famhealth.util.RequestUserResolver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/family")
@RequiredArgsConstructor
public class FamilyController {
    private final FamilyService service;
    private final RequestUserResolver resolver;

    @PostMapping("/share") public FamilyAccessResponse share(@RequestBody ShareRequest req,@RequestHeader("X-User-Id") String uid,@RequestHeader("X-Account-Id") String aid){ return service.share(req, resolver.fromHeaders(uid, aid)); }
    @GetMapping("/access") public List<FamilyAccessResponse> access(@RequestHeader("X-User-Id") String uid,@RequestHeader("X-Account-Id") String aid){ return service.list(resolver.fromHeaders(uid, aid)); }
    @PutMapping("/share/{id}/revoke") public void revoke(@PathVariable Long id,@RequestHeader("X-User-Id") String uid,@RequestHeader("X-Account-Id") String aid){ service.revoke(id, resolver.fromHeaders(uid, aid)); }
}
