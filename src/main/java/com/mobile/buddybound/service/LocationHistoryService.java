package com.mobile.buddybound.service;

import com.mobile.buddybound.model.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface LocationHistoryService {
    ResponseEntity<ApiResponse> getUserLocationHistory(Long userId);
    ResponseEntity<ApiResponse> saveCurrentLocation();
}
