package com.famhealth.controller;

import com.famhealth.dto.RecordDtos.*;
import com.famhealth.service.RecordService;
import com.famhealth.util.RequestUserResolver;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class RecordController {
    private final RecordService service;
    private final RequestUserResolver resolver;

    @PostMapping(value = "/api/records/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public RecordUploadResponse upload(@RequestParam(required = false) Long profileId, @RequestParam(required = false) String recordType, @RequestParam(required = false) String providerName,
                                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate recordDate, @RequestPart MultipartFile file,
                                       @RequestHeader("X-User-Id") String uid, @RequestHeader("X-Account-Id") String aid){
        return service.upload(profileId, recordType, providerName, recordDate, file, resolver.fromHeaders(uid, aid));
    }
    @GetMapping("/api/records") public List<RecordResponse> list(@RequestHeader("X-User-Id") String uid,@RequestHeader("X-Account-Id") String aid){ return service.list(resolver.fromHeaders(uid, aid)); }
    @GetMapping("/api/records/{id}") public RecordResponse get(@PathVariable Long id,@RequestHeader("X-User-Id") String uid,@RequestHeader("X-Account-Id") String aid){ return service.get(id, resolver.fromHeaders(uid, aid)); }
    @GetMapping("/api/records/{id}/file") public java.util.Map<String, String> file(@PathVariable Long id,@RequestHeader("X-User-Id") String uid,@RequestHeader("X-Account-Id") String aid){ return java.util.Map.of("url", service.fileUrl(id, resolver.fromHeaders(uid, aid))); }
    @PostMapping("/api/records/{id}/process") public void process(@PathVariable Long id,@RequestHeader("X-User-Id") String uid,@RequestHeader("X-Account-Id") String aid){ service.process(id, resolver.fromHeaders(uid, aid)); }
}
