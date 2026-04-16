package com.famhealth.controller;

import com.famhealth.dto.RecordDtos.*;
import com.famhealth.service.ObservationService;
import com.famhealth.util.RequestUserResolver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ObservationController {
    private final ObservationService service;
    private final RequestUserResolver resolver;

    @GetMapping("/api/profiles/{id}/observations")
    public List<ObservationResponse> observations(@PathVariable Long id,@RequestHeader("X-User-Id") String uid,@RequestHeader("X-Account-Id") String aid){ return service.byProfile(id, resolver.fromHeaders(uid, aid)); }
    @PutMapping("/api/observations/{id}/review")
    public void review(@PathVariable Long id,@RequestBody ObservationReviewRequest req,@RequestHeader("X-User-Id") String uid,@RequestHeader("X-Account-Id") String aid){ service.review(id, req, resolver.fromHeaders(uid, aid)); }
    @GetMapping("/api/profiles/{id}/dashboard")
    public DashboardResponse dashboard(@PathVariable Long id,@RequestHeader("X-User-Id") String uid,@RequestHeader("X-Account-Id") String aid){ return service.dashboard(id, resolver.fromHeaders(uid, aid)); }
    @GetMapping("/api/profiles/{id}/timeline")
    public List<ObservationResponse> timeline(@PathVariable Long id,@RequestHeader("X-User-Id") String uid,@RequestHeader("X-Account-Id") String aid){ return service.byProfile(id, resolver.fromHeaders(uid, aid)); }
}
