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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemorableDestinationServiceImpl implements MemorableDestinationService {
    private final MemorableDestinationRepository memorableDestinationRepository;
    private final MemorableDestinationMapper memorableDestinationMapper;
    private final UserService userService;

    private static double RADIUS;

    @Override
    @Transactional
    @CacheEvict(value = {"destinations"}, allEntries = true)
    public ResponseEntity<?> createDestination(MemorableDestinationDto memorableDestinationDto) {
        var currentUser = userService.getCurrentLoggedInUser();
        MemorableDestination entity = memorableDestinationMapper.toEntity(memorableDestinationDto);
        entity.setUser(currentUser);
        entity = memorableDestinationRepository.save(entity);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "create destination", memorableDestinationMapper.toDto(entity)));
    }

    @Override
    @Transactional
    @CacheEvict(value = {"destinations"}, allEntries = true)
    public ResponseEntity<?> updateDestination(MemorableDestinationDto memorableDestinationDto) {
        var currentUser = userService.getCurrentLoggedInUser();
        var destination = currentUser.getMemorableDestinations().stream().filter(d -> d.getId().equals(memorableDestinationDto.getId())).findFirst();
        if (destination.isEmpty()) {
            throw new NotFoundException("Can't find the destination");
        }
        MemorableDestination destinationEntity = destination.get();
        destinationEntity.setNote(memorableDestinationDto.getNote());
        destinationEntity.setLocationType(memorableDestinationDto.getLocationType());
        destinationEntity = memorableDestinationRepository.save(destinationEntity);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "update destination", memorableDestinationMapper.toDto(destinationEntity)));
    }

    @Override
    @Transactional
    @CacheEvict(value = {"destinations"}, allEntries = true)
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
    @Cacheable(value = "destinations", key = "#type + '-' + #currentUserId")
    public List<MemorableDestinationDto> getAllDestinations(Long currentUserId, MemorableDestinationType type) {
        List<MemorableDestination> destinations = (type != null)
                ? memorableDestinationRepository.getAllByUser_IdAndLocationType(currentUserId, type)
                : memorableDestinationRepository.getAllByUser_Id(currentUserId);

        return destinations.stream()
                .map(memorableDestinationMapper::toDto)
                .toList();
    }

//    @Override
//    public ResponseEntity<?> getNearbyDestinations(Double latitude, Double longitude, MemorableDestinationType type) {
//        var currentUserId = userService.getCurrentLoggedInUser().getId();
//        List<MemorableDestinationDto> dtoList = memorableDestinationRepository.findNearbyDestinationsByType(currentUserId, latitude, longitude, type)
//                .stream()
//                .map(memorableDestinationMapper::toDto)
//                .toList();
//        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Get nearby locations", dtoList));
//    }
}
