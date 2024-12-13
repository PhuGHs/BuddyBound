package com.mobile.buddybound.service;

import com.mobile.buddybound.model.dto.LocationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public interface LocationHistoryService {
    ResponseEntity<?> getUserLocationHistory(LocalDate from, LocalDate to);
    void saveCurrentLocation();
}
