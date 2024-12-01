package com.mobile.buddybound.service;

import com.mobile.buddybound.model.dto.LocationDto;
import com.mobile.buddybound.model.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface LocationHistoryService {
    ResponseEntity<?> getUserLocationHistory(Long userId);
    ResponseEntity<?> saveCurrentLocation(LocationDto dto);
}
