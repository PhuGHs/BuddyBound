package com.mobile.buddybound.service;

import com.mobile.buddybound.model.dto.LocationDto;
import org.springframework.stereotype.Service;

@Service
public interface LocationService {
    void updateUserLocation(LocationDto dto);
    void turnOffUserLocation();
}
