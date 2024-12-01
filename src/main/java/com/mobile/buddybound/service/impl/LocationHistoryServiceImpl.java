package com.mobile.buddybound.service.impl;

import com.mobile.buddybound.model.dto.LocationDto;
import com.mobile.buddybound.model.entity.LocationHistory;
import com.mobile.buddybound.model.response.ApiResponse;
import com.mobile.buddybound.model.response.ApiResponseStatus;
import com.mobile.buddybound.repository.LocationHistoryRepository;
import com.mobile.buddybound.service.LocationHistoryService;
import com.mobile.buddybound.service.mapper.LocationHistoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationHistoryServiceImpl implements LocationHistoryService {
    private LocationHistoryRepository locationHistoryRepository;
    private LocationHistoryMapper locationHistoryMapper;
    @Override
    public ResponseEntity<?> getUserLocationHistory(Long userId) {
        List<LocationHistory> locationList = locationHistoryRepository.findByUserId(userId);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Get all location history", locationList.stream().map(locationHistoryMapper::toDto)));
    }

    @Override
    public ResponseEntity<?> saveCurrentLocation(LocationDto dto) {
        return null;
    }
}
