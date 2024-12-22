package com.mobile.buddybound.service.impl;

import com.mobile.buddybound.exception.BadRequestException;
import com.mobile.buddybound.model.dto.LocationDto;
import com.mobile.buddybound.model.entity.Location;
import com.mobile.buddybound.model.response.ApiResponse;
import com.mobile.buddybound.model.response.ApiResponseStatus;
import com.mobile.buddybound.pattern.observer.GroupSubscriber;
import com.mobile.buddybound.pattern.observer.Publisher;
import com.mobile.buddybound.repository.LocationRepository;
import com.mobile.buddybound.service.LocationService;
import com.mobile.buddybound.service.UserService;
import com.mobile.buddybound.service.mapper.LocationMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationServiceImpl implements LocationService {
    private final Publisher publisher;
    private final GroupSubscriber groupSubscriber;
    private final LocationMapper locationMapper;
    private final LocationRepository locationRepository;
    private final UserService userService;

    @PostConstruct
    public void init() {
        publisher.subscribe(groupSubscriber);
    }

    @Override
    public void updateUserLocation(LocationDto dto) {
        var currentUser = userService.getCurrentLoggedInUser();
        if (Objects.isNull(currentUser.getSettings()) || !currentUser.getSettings().isLocationEnabled()) {
            log.info("{} has turned off location tracking permission!", currentUser.getFullName());
            return;
        }
        Location location = locationRepository.findByUser_Id(currentUser.getId())
                .orElse(null);
        if (Objects.isNull(location)) {
            Location newLocation = Location.builder()
                    .user(currentUser)
                    .longitude(dto.getLongitude())
                    .latitude(dto.getLatitude())
                    .timestamp(LocalDateTime.now())
                    .build();
            publisher.notify(locationMapper.toDto(locationRepository.save(newLocation)));
            return;
        }
        location.setLatitude(dto.getLatitude());
        location.setLongitude(dto.getLongitude());
        location.setTimestamp(LocalDateTime.now());
        publisher.notify(locationMapper.toDto(locationRepository.save(location)));
    }

    @Override
    public void turnOffUserLocation() {

    }

    @Override
    public ResponseEntity<?> loadMap(Long groupId) {
        List<Location> locations = locationRepository.getUserLocationsWithinGroup(groupId);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "get locations", locations.stream().map(locationMapper::toDto)));
    }

    @Override
    public ResponseEntity<?> getUserLocation(Long userId) {
        if (Objects.isNull(userId)) {
            var currentUser = userService.getCurrentLoggedInUser();
            return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "get user location", locationMapper.toDto(currentUser.getLocation())));
        }
        var user = userService.findById(userId);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "get user location", locationMapper.toDto(user.getLocation())));
    }
}
