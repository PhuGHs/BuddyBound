package com.mobile.buddybound.service;

import com.mobile.buddybound.model.dto.LocationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface LocationService {
    void updateUserLocation(LocationDto dto);
    void turnOffUserLocation();
    ResponseEntity<?> loadMap(Long groupId);
    ResponseEntity<?> getUserLocation(Long userId);
}
