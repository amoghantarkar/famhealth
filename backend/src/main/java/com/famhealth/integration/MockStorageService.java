package com.famhealth.integration;

import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MockStorageService implements StorageService {
    @Override
    public String upload(MultipartFile file) { return "records/" + UUID.randomUUID() + "-" + file.getOriginalFilename(); }
    @Override
    public String signedDownloadUrl(String key) { return "/api/records/file-proxy?key=" + key + "&sig=mock"; }
}
