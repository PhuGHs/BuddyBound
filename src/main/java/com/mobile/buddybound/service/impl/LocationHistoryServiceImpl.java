package com.mobile.buddybound.service.impl;

import com.mobile.buddybound.model.dto.LocationDto;
import com.mobile.buddybound.model.entity.LocationHistory;
import com.mobile.buddybound.model.response.ApiResponse;
import com.mobile.buddybound.model.response.ApiResponseStatus;
import com.mobile.buddybound.repository.LocationHistoryRepository;
import com.mobile.buddybound.repository.LocationRepository;
import com.mobile.buddybound.repository.UserRepository;
import com.mobile.buddybound.service.CalculateDistanceService;
import com.mobile.buddybound.service.LocationHistoryService;
import com.mobile.buddybound.service.LocationService;
import com.mobile.buddybound.service.UserService;
import com.mobile.buddybound.service.mapper.LocationHistoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationHistoryServiceImpl implements LocationHistoryService {
    private final LocationHistoryRepository locationHistoryRepository;
    private final LocationHistoryMapper locationHistoryMapper;
    private final UserRepository userRepository;
    private final UserService userService;
    private final double DISTANCE = 0.1; //100m

    @Override
    public ResponseEntity<?> getUserLocationHistory(LocalDate startDate, LocalDate endDate) {
        var currentUserId = userService.getCurrentLoggedInUser().getId();
        List<LocationHistory> locationList;
        if (Objects.isNull(startDate) && Objects.isNull(endDate)) {
            locationList = locationHistoryRepository.findByUser_Id(currentUserId);
        } else if (!Objects.isNull(startDate) && Objects.isNull(endDate)) {
            locationList = locationHistoryRepository.findByUser_IdAndCreatedAtGreaterThanEqual(currentUserId, startDate.atStartOfDay());
        } else if (Objects.isNull(startDate) && !Objects.isNull(endDate)) {
            locationList = locationHistoryRepository.findByUser_IdAndCreatedAtLessThanEqual(currentUserId, endDate.atTime(LocalTime.MAX));
        } else {
            locationList = locationHistoryRepository.findByUser_IdAndCreatedAtBetween(currentUserId, startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX));
        }
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Get all location history", locationList.stream().map(locationHistoryMapper::toDto)));
    }

    @Override
    @Scheduled(fixedRate = 180000)
    public void saveCurrentLocation() {
        var users = userRepository.findAll();
        users.forEach(user -> {
            var location = user.getLocation();
            if (!Objects.isNull(location) && !Objects.isNull(user.getSettings()) && user.getSettings().isLocationHistoryEnabled()) {
                var locationHistory = locationHistoryRepository.findLatestLocationHistoryByUserId(user.getId()).orElse(null);
                if (Objects.isNull(locationHistory)) {
                    return;
                }
                if (CalculateDistanceService.haversine(locationHistory.getLatitude(), locationHistory.getLongitude(), location.getLatitude(), location.getLongitude(), DISTANCE)) {
                    LocationHistory history = LocationHistory.builder()
                            .user(user)
                            .longitude(location.getLongitude())
                            .latitude(location.getLatitude())
                            .build();
                    log.info("Saving user with Id {} location history: {}", user.getId(), history);
                    locationHistoryRepository.save(history);
                } else {
                    locationHistory.setCreatedAt(LocalDateTime.now());
                    locationHistoryRepository.save(locationHistory);
                }
                log.info("Location is not saved");
            }
        });
    }
}
