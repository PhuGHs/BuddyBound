package com.mobile.buddybound.service.impl;

import com.mobile.buddybound.exception.NotFoundException;
import com.mobile.buddybound.model.dto.RelationshipDto;
import com.mobile.buddybound.model.entity.FamilyRelationship;
import com.mobile.buddybound.model.entity.Relationship;
import com.mobile.buddybound.model.entity.User;
import com.mobile.buddybound.model.enumeration.RelationshipType;
import com.mobile.buddybound.pattern.abstract_factory.FamilyRelationshipFactory;
import com.mobile.buddybound.pattern.abstract_factory.FriendRelationshipFactory;
import com.mobile.buddybound.repository.RelationshipRepository;
import com.mobile.buddybound.repository.UserRepository;
import com.mobile.buddybound.service.RelationshipService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class RelationshipServiceImpl implements RelationshipService {
    private final FamilyRelationshipFactory familyRelationshipFactory;
    private final RelationshipRepository relationshipRepository;
    private final FriendRelationshipFactory friendRelationshipFactory;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<?> addRelationship(RelationshipDto dto) {
        User sender = userRepository.findById(dto.getSenderId())
                .orElseThrow(() -> new NotFoundException("sender not found"));
        if (!userRepository.existsById(dto.getReceiverId())) {
            throw new NotFoundException("receiver not found");
        }

        Relationship relationship = null;
        if (dto.getRelationshipType().equals(RelationshipType.FAMILY)) {
            relationship = familyRelationshipFactory.createRelationship(dto);
            relationship.setContent(sender.getFullName() + " would like to set relationship to you as " + dto.getFamilyType());
        } else {
            relationship = friendRelationshipFactory.createRelationship(dto);
            relationship.setContent(sender.getFullName() + " would like to set relationship to you as " + dto.getFriendType());
        }

        Relationship savedOne = relationshipRepository.save(relationship);
        return ResponseEntity.ok().body(savedOne);
    }
}
