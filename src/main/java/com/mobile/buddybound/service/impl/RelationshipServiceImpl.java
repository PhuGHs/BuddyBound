package com.mobile.buddybound.service.impl;

import com.mobile.buddybound.controller.validation.RelationshipValidator;
import com.mobile.buddybound.exception.BadRequestException;
import com.mobile.buddybound.exception.NotFoundException;
import com.mobile.buddybound.model.dto.BlockedRelationshipDto;
import com.mobile.buddybound.model.dto.NotificationData;
import com.mobile.buddybound.model.dto.RelationshipDto;
import com.mobile.buddybound.model.entity.*;
import com.mobile.buddybound.model.enumeration.FamilyType;
import com.mobile.buddybound.model.enumeration.NotificationType;
import com.mobile.buddybound.model.enumeration.RelationshipType;
import com.mobile.buddybound.model.response.ApiResponse;
import com.mobile.buddybound.model.response.ApiResponseStatus;
import com.mobile.buddybound.pattern.abstract_factory.FamilyRelationshipFactory;
import com.mobile.buddybound.pattern.abstract_factory.FriendRelationshipFactory;
import com.mobile.buddybound.pattern.factory_method.NotificationFactory;
import com.mobile.buddybound.pattern.factory_method.NotificationFactoryProvider;
import com.mobile.buddybound.pattern.strategy.relationship_strategy.RelationshipStrategy;
import com.mobile.buddybound.pattern.strategy.relationship_strategy.RelationshipStrategyContext;
import com.mobile.buddybound.repository.*;
import com.mobile.buddybound.repository.specification.RelationshipSpecification;
import com.mobile.buddybound.service.NotificationService;
import com.mobile.buddybound.service.RelationshipService;
import com.mobile.buddybound.service.UserService;
import com.mobile.buddybound.service.mapper.BlockedRelationshipMapper;
import com.mobile.buddybound.service.mapper.RelationshipMapper;
import com.mobile.buddybound.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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
    private final NotificationService notificationService;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public Object addRelationship(RelationshipDto dto) {
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

        Relationship savedOne = relationshipRepository.save(relationship);

        //send notification
        NotificationData data = NotificationData.builder()
                .senderId(sender.getId())
                .recipientId(dto.getReceiverId())
                .referenceId(savedOne.getId())
                .requesterName(savedOne.getSender().getFullName())
                .build();
        notificationService.sendNotification(NotificationType.RELATIONSHIP_REQUEST, data);

        return !this.isFamily(dto.getRelationshipType()) ? relationshipMapper.toFriendRelationshipDto((FriendRelationship) savedOne) : relationshipMapper.toFamilyRelationshipDto((FamilyRelationship) savedOne);
    }

    @Override
    public List<RelationshipDto> getAllRelationship(Long currentUserId, String searchTerm, boolean isPending, RelationshipType type) {

        // Normalize searchTerm to handle null or blank values
        String normalizedSearchTerm = (searchTerm != null && !searchTerm.isBlank()) ? searchTerm : null;

        RelationshipStrategy strategy = relationshipStrategyContext.getStrategy(type);
        return strategy.getRelationships(currentUserId, normalizedSearchTerm, isPending);
    }


    @Override
    @Transactional
    @CacheEvict(value = "relationships", allEntries = true)
    public Relationship updateRelationship(RelationshipDto dto) {
        User user = userService.getCurrentLoggedInUser();
        Relationship relationship = getRelationshipByType(dto);

        if (!relationship.getSender().getId().equals(dto.getReceiverId())
                && !relationship.getReceiver().getId().equals(dto.getReceiverId())) {
            throw new BadRequestException("You are not within this relationship");
        }

        return relationship;
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

    @Override
    public FamilyRelationship getFamilyRelationshipBetweenTwoPeople(Long currentUserId, Long userId) {
        return familyRelationshipRepository.findRelationship(currentUserId, userId);
    }

    @Override
    public FriendRelationship getFriendRelationshipBetweenTwoPeople(Long currentUserId, Long userId) {
        return friendRelationshipRepository.getFriendRelationship(currentUserId, userId);
    }

    @Override
    public ResponseEntity<?> acceptRelationship(Long relationshipId) {
        var currentUserId = userService.getCurrentLoggedInUser().getId();
        var relationship = relationshipRepository.findById(relationshipId)
                .orElseThrow(() -> new NotFoundException("Relationship not found"));
        if (!relationship.getReceiver().getId().equals(currentUserId)) {
            throw new BadRequestException("You are not the receiver");
        }
        relationship.setPending(false);
        relationshipRepository.save(relationship);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> getPendingRelationship() {
        var currentUser = userService.getCurrentLoggedInUser();
        var pendingRelationshipRequests = currentUser.getBackwardRelationships()
                .stream()
                .map(r -> {
                    if (!r.isPending()) {
                        return null;
                    }
                    RelationshipDto relationshipDto = null;
                    if (r instanceof FriendRelationship fr) {
                        relationshipDto = relationshipMapper.toFriendRelationshipDto(fr);
                    } else if (r instanceof FamilyRelationship fr) {
                        relationshipDto = relationshipMapper.toFamilyRelationshipDto(fr);
                    }
                    assert relationshipDto != null;
                    relationshipDto.setReceiver(userMapper.toDto(r.getSender()));
                    return relationshipDto;
                })
                .filter(Objects::nonNull)
                .toList();
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "get pending relationships", pendingRelationshipRequests));
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
