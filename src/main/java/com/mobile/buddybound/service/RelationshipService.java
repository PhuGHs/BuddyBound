package com.mobile.buddybound.service;

import com.mobile.buddybound.model.dto.RelationshipDto;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface RelationshipService {
    ResponseEntity<?> addRelationship(RelationshipDto dto);
}
