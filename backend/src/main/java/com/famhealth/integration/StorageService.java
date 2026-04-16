package com.famhealth.integration;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String upload(MultipartFile file);
    String signedDownloadUrl(String key);
}
