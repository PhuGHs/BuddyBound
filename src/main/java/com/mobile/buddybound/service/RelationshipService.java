package com.mobile.buddybound.service;

import com.mobile.buddybound.model.dto.BlockedRelationshipDto;
import com.mobile.buddybound.model.dto.RelationshipDto;
import com.mobile.buddybound.model.entity.FamilyRelationship;
import com.mobile.buddybound.model.entity.FriendRelationship;
import com.mobile.buddybound.model.entity.Relationship;
import com.mobile.buddybound.model.enumeration.RelationshipType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RelationshipService {
    Object addRelationship(RelationshipDto dto);
    List<RelationshipDto> getAllRelationship(Long currentUserId, String searchTerm, boolean isPending, RelationshipType type);
    Relationship updateRelationship(RelationshipDto dto);
    ResponseEntity<?> limitOrUnlimitRelationship(BlockedRelationshipDto dto);
    ResponseEntity<?> getUserLimitedRelationshipList();
    FamilyRelationship getFamilyRelationshipBetweenTwoPeople(Long currentUserId, Long userId);
    FriendRelationship getFriendRelationshipBetweenTwoPeople(Long currentUserId, Long userId);
    ResponseEntity<?> acceptRelationship(Long relationshipId);
    ResponseEntity<?> getPendingRelationship();
}
