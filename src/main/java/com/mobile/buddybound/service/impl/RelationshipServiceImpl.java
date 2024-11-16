package com.mobile.buddybound.service.impl;

import com.mobile.buddybound.exception.BadRequestException;
import com.mobile.buddybound.exception.NotFoundException;
import com.mobile.buddybound.model.dto.RelationshipDto;
import com.mobile.buddybound.model.entity.FamilyRelationship;
import com.mobile.buddybound.model.entity.FriendRelationship;
import com.mobile.buddybound.model.entity.Relationship;
import com.mobile.buddybound.model.entity.User;
import com.mobile.buddybound.model.enumeration.FamilyType;
import com.mobile.buddybound.model.enumeration.RelationshipType;
import com.mobile.buddybound.model.response.ApiResponse;
import com.mobile.buddybound.model.response.ApiResponseStatus;
import com.mobile.buddybound.pattern.abstract_factory.FamilyRelationshipFactory;
import com.mobile.buddybound.pattern.abstract_factory.FriendRelationshipFactory;
import com.mobile.buddybound.repository.FamilyRelationshipRepository;
import com.mobile.buddybound.repository.FriendRelationshipRepository;
import com.mobile.buddybound.repository.RelationshipRepository;
import com.mobile.buddybound.repository.UserRepository;
import com.mobile.buddybound.service.RelationshipService;
import com.mobile.buddybound.service.UserService;
import com.mobile.buddybound.service.mapper.RelationshipMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class RelationshipServiceImpl implements RelationshipService {
    private final FamilyRelationshipFactory familyRelationshipFactory;
    private final RelationshipRepository relationshipRepository;
    private final FriendRelationshipFactory friendRelationshipFactory;
    private final UserRepository userRepository;
    private final RelationshipMapper relationshipMapper;
    private final FamilyRelationshipRepository familyRelationshipRepository;
    private final FriendRelationshipRepository friendRelationshipRepository;
    private final UserService userService;

    @Override
    public ResponseEntity<?> addRelationship(RelationshipDto dto) {
        User sender = userService.getCurrentLoggedInUser();
        if (!userRepository.existsById(dto.getReceiverId())) {
            throw new NotFoundException("receiver not found");
        }

        if (sender.getId().equals(dto.getReceiverId())) {
            throw new BadRequestException("You can't set relationship to yourself");
        }

        if (relationshipRepository.checkIfExist(sender.getId(), dto.getReceiverId())) {
            throw new BadRequestException("This relationship was created before");
        }

        Relationship relationship = null;
        if (this.isFamily(dto.getRelationshipType())) {
            relationship = familyRelationshipFactory.createRelationship(dto);
            if (dto.getFamilyType().equals(FamilyType.PARENT_CHILD)) {
                relationship.setPending(false);
            }
            relationship.setSender(sender);
            relationship.setContent(sender.getFullName() + " would like to set relationship to you as " + dto.getFamilyType());
        } else {
            relationship = friendRelationshipFactory.createRelationship(dto);
            relationship.setSender(sender);
            relationship.setContent(sender.getFullName() + " would like to set relationship to you as " + dto.getFriendType());
        }
        Relationship savedOne = relationshipRepository.save(relationship);
        Object returnObject = !this.isFamily(dto.getRelationshipType()) ? relationshipMapper.toFriendRelationshipDto((FriendRelationship) savedOne) : relationshipMapper.toFamilyRelationshipDto((FamilyRelationship) savedOne);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "add Relationship", returnObject));
    }

    @Override
    @Transactional
    public ResponseEntity<?> getAllRelationship(boolean isPending, RelationshipType type) {
        Long currentUserId = userService.getCurrentLoggedInUser().getId();
        if (this.isFamily(type)) {
            return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Get all family relationship", familyRelationshipRepository.getAll(isPending, currentUserId).stream().map(relationshipMapper::toFamilyRelationshipDto)));
        }
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Get all friend relationship", friendRelationshipRepository.getAll(isPending, currentUserId).stream().map(relationshipMapper::toFriendRelationshipDto)));
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateRelationship(RelationshipDto dto) {
        User user = userService.getCurrentLoggedInUser();
        Relationship relationship = getRelationshipByType(dto);

        if (!relationship.getSender().getId().equals(dto.getReceiverId())
                && !relationship.getReceiver().getId().equals(dto.getReceiverId())) {
            throw new BadRequestException("You are not within this relationship");
        }

        if (relationship instanceof FamilyRelationship) {
            return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "update Relationship", relationshipMapper.toFamilyRelationshipDto((FamilyRelationship) relationship)));
        }
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "update relationship", relationshipMapper.toFriendRelationshipDto((FriendRelationship) relationship)));
    }

    private boolean isFamily(RelationshipType type) {
        return type.equals(RelationshipType.FAMILY);
    }

    private Relationship getRelationshipByType(RelationshipDto dto) {
        if (isFamily(dto.getRelationshipType())) {
            FamilyRelationship family = familyRelationshipRepository.findById(dto.getId())
                    .orElseThrow(() -> new NotFoundException("Family relationship not found"));
            family.setFamilyType(dto.getFamilyType());
            family.setUpdatedAt(LocalDateTime.now());
            return familyRelationshipRepository.save(family);
        } else {
            FriendRelationship friend = friendRelationshipRepository.findById(dto.getId())
                    .orElseThrow(() -> new NotFoundException("Friend relationship not found"));
            friend.setFriendType(dto.getFriendType());
            friend.setUpdatedAt(LocalDateTime.now());
            return friendRelationshipRepository.save(friend);
        }
    }
}
