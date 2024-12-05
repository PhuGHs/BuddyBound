package com.mobile.buddybound.service.impl;

import com.mobile.buddybound.exception.BadRequestException;
import com.mobile.buddybound.exception.NotFoundException;
import com.mobile.buddybound.model.dto.MemorableDestinationDto;
import com.mobile.buddybound.model.entity.MemorableDestination;
import com.mobile.buddybound.model.enumeration.MemorableDestinationType;
import com.mobile.buddybound.model.response.ApiResponse;
import com.mobile.buddybound.model.response.ApiResponseStatus;
import com.mobile.buddybound.repository.MemorableDestinationRepository;
import com.mobile.buddybound.service.MemorableDestinationService;
import com.mobile.buddybound.service.UserService;
import com.mobile.buddybound.service.mapper.MemorableDestinationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemorableDestinationServiceImpl implements MemorableDestinationService {
    private final MemorableDestinationRepository memorableDestinationRepository;
    private final MemorableDestinationMapper memorableDestinationMapper;
    private final UserService userService;

    private static double RADIUS;

    @Override
    @Transactional
    public ResponseEntity<?> createDestination(MemorableDestinationDto memorableDestinationDto) {
        var currentUser = userService.getCurrentLoggedInUser();
        MemorableDestination entity = memorableDestinationMapper.toEntity(memorableDestinationDto);
        entity.setUser(currentUser);
        entity = memorableDestinationRepository.save(entity);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "create destination", memorableDestinationMapper.toDto(entity)));
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteDestination(Long id) {
        var currentUserId = userService.getCurrentLoggedInUser().getId();
        var destination = memorableDestinationRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("destination not found"));
        if (!destination.getUser().getId().equals(currentUserId)) {
            throw new BadRequestException("You do not own this destination");
        }
        memorableDestinationRepository.delete(destination);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> getAllDestinations(MemorableDestinationType type) {
        var currentUser = userService.getCurrentLoggedInUser();
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Get all", currentUser.getMemorableDestinations().stream().map(memorableDestinationMapper::toDto)));
    }

    @Override
    public ResponseEntity<?> getNearbyDestinations(Double latitude, Double longitude, MemorableDestinationType type) {
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Get nearby locations", memorableDestinationRepository.findNearbyDestinationsByType(latitude, longitude, type)));
    }
}
