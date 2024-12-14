package com.mobile.buddybound.service;

import com.mobile.buddybound.model.dto.MemorableDestinationDto;
import com.mobile.buddybound.model.entity.MemorableDestination;
import com.mobile.buddybound.model.enumeration.MemorableDestinationType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MemorableDestinationService {
    ResponseEntity<?> createDestination(MemorableDestinationDto memorableDestinationDto);
    ResponseEntity<?> updateDestination(MemorableDestinationDto memorableDestinationDto);
    ResponseEntity<?> deleteDestination(Long id);
    List<MemorableDestinationDto> getAllDestinations(Long currentUserId, MemorableDestinationType type);
//    ResponseEntity<?> getNearbyDestinations(Double latitude, Double longitude, MemorableDestinationType type);
}
