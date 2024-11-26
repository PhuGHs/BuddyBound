package com.mobile.buddybound.service.impl;

import com.mobile.buddybound.controller.validation.RelationshipValidator;
import com.mobile.buddybound.exception.BadRequestException;
import com.mobile.buddybound.exception.NotFoundException;
import com.mobile.buddybound.model.dto.BlockedRelationshipDto;
import com.mobile.buddybound.model.dto.RelationshipDto;
import com.mobile.buddybound.model.entity.*;
import com.mobile.buddybound.model.enumeration.FamilyType;
import com.mobile.buddybound.model.enumeration.RelationshipType;
import com.mobile.buddybound.model.response.ApiResponse;
import com.mobile.buddybound.model.response.ApiResponseStatus;
import com.mobile.buddybound.pattern.abstract_factory.FamilyRelationshipFactory;
import com.mobile.buddybound.pattern.abstract_factory.FriendRelationshipFactory;
import com.mobile.buddybound.pattern.strategy.relationship_strategy.RelationshipStrategy;
import com.mobile.buddybound.pattern.strategy.relationship_strategy.RelationshipStrategyContext;
import com.mobile.buddybound.repository.*;
import com.mobile.buddybound.repository.specification.RelationshipSpecification;
import com.mobile.buddybound.service.RelationshipService;
import com.mobile.buddybound.service.UserService;
import com.mobile.buddybound.service.mapper.BlockedRelationshipMapper;
import com.mobile.buddybound.service.mapper.RelationshipMapper;
import com.mobile.buddybound.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    private final BlockedRelationshipMapper blockedRelationshipMapper;
    private final BlockedRelationshipRepository blockedRelationshipRepository;
    private final RelationshipStrategyContext relationshipStrategyContext;

    @Override
    @Transactional
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
            RelationshipValidator.validateFamilyRelationship(dto);
            relationship = familyRelationshipFactory.createRelationship(dto);
            if (dto.getFamilyType().equals(FamilyType.PARENT_CHILD)) {
                relationship.setPending(false);
            }
            relationship.setSender(sender);
        } else {
            relationship = friendRelationshipFactory.createRelationship(dto);
            relationship.setSender(sender);
        }

        //add notification here
        Relationship savedOne = relationshipRepository.save(relationship);
        Object returnObject = !this.isFamily(dto.getRelationshipType()) ? relationshipMapper.toFriendRelationshipDto((FriendRelationship) savedOne) : relationshipMapper.toFamilyRelationshipDto((FamilyRelationship) savedOne);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "add Relationship", returnObject));
    }

    @Override
    public ResponseEntity<?> getAllRelationship(String searchTerm, boolean isPending, RelationshipType type) {
        Long currentUserId = userService.getCurrentLoggedInUser().getId();

        RelationshipStrategy strategy = relationshipStrategyContext.getStrategy(type);
        List<RelationshipDto> dtos = strategy.getRelationships(currentUserId, searchTerm, isPending);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Relationships returned", dtos));
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

    @Override
    @Transactional
    public ResponseEntity<?> limitOrUnlimitRelationship(BlockedRelationshipDto dto) {
        if (blockedRelationshipRepository.existsById(dto.getId())) {
            blockedRelationshipRepository.deleteById(dto.getId());
            return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Break restriction successfully!", ""));
        }
        User user = userService.getCurrentLoggedInUser();
        BlockedRelationship entity = blockedRelationshipMapper.toEntity(dto);
        entity.setUser(user);
        entity = blockedRelationshipRepository.save(entity);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Restricted this user", blockedRelationshipMapper.toDto(entity)));
    }

    @Override
    public ResponseEntity<?> getUserLimitedRelationshipList() {
        User user = userService.getCurrentLoggedInUser();
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Get all restricted users", user.getBlockedRelationships().stream().map(blockedRelationshipMapper::toDto)));
    }

    private boolean isFamily(RelationshipType type) {
        return type.equals(RelationshipType.FAMILY);
    }

    private Relationship getRelationshipByType(RelationshipDto dto) {
        if (isFamily(dto.getRelationshipType())) {
            RelationshipValidator.validateFamilyRelationship(dto);
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
