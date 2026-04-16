package com.famhealth.controller;

import com.famhealth.service.PrivacyService;
import com.famhealth.util.RequestUserResolver;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PrivacyController {
    private final PrivacyService service;
    private final RequestUserResolver resolver;

    @GetMapping("/api/settings/privacy") public Map<String, String> privacy(){ return service.privacy(); }
    @PostMapping("/api/account/delete") public void delete(@RequestHeader("X-User-Id") String uid,@RequestHeader("X-Account-Id") String aid){ service.deleteAccount(resolver.fromHeaders(uid, aid)); }
}
