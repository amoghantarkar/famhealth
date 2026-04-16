package com.famhealth.util;

import com.famhealth.exception.ApiException;
import org.springframework.stereotype.Component;

@Component
public class RequestUserResolver {
    public RequestUser fromHeaders(String userHeader, String accountHeader){
        try {
            return new RequestUser(Long.parseLong(userHeader), Long.parseLong(accountHeader));
        } catch (Exception e){
            throw new ApiException("Missing or invalid X-User-Id / X-Account-Id headers for MVP");
        }
    }
}
