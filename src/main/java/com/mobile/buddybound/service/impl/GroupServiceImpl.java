package com.mobile.buddybound.service.impl;

import com.mobile.buddybound.exception.BadRequestException;
import com.mobile.buddybound.exception.NotFoundException;
import com.mobile.buddybound.model.dto.*;
import com.mobile.buddybound.model.entity.*;
import com.mobile.buddybound.model.enumeration.GroupType;
import com.mobile.buddybound.model.enumeration.NotificationType;
import com.mobile.buddybound.model.enumeration.RelationshipType;
import com.mobile.buddybound.model.response.ApiResponse;
import com.mobile.buddybound.model.response.ApiResponseStatus;
import com.mobile.buddybound.repository.GroupRepository;
import com.mobile.buddybound.repository.MemberRepository;
import com.mobile.buddybound.repository.RelationshipRepository;
import com.mobile.buddybound.service.GroupService;
import com.mobile.buddybound.service.NotificationService;
import com.mobile.buddybound.service.RelationshipService;
import com.mobile.buddybound.service.UserService;
import com.mobile.buddybound.service.mapper.GroupMapper;
import com.mobile.buddybound.service.mapper.MemberMapper;
import com.mobile.buddybound.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final UserService userService;
    private final GroupMapper groupMapper;
    private final MemberMapper memberMapper;
    private final UserMapper userMapper;
    private final NotificationService notificationService;
    private final RelationshipService relationshipService;
    private final RelationshipRepository relationshipRepository;

    @Override
    @Transactional
    public ResponseEntity<?> createGroup(GroupDto dto) {
        var user = userService.getCurrentLoggedInUser();
        var currentUserId = user.getId();

        if (!dto.getGroupType().equals(GroupType.ONE_TO_ONE) && dto.getUserIds().toArray().length <= 1) {
            throw new BadRequestException("The number of members must be over 2");
        }

        if (dto.getGroupType().equals(GroupType.FAMILY)) {
            if (memberRepository.existsByUser_IdAndGroup_GroupType(currentUserId, GroupType.FAMILY)) {
                throw new BadRequestException("A user can only create or join exactly one family group.");
            }
        }

        Group group = groupRepository.save(groupMapper.toEntity(dto));
        group.setOwner(user);

        var userIds = Stream.concat(Stream.of(currentUserId), dto.getUserIds().stream()).toList();
        var members = userIds.stream().map(userId -> Member.builder()
                .user(userService.findById(userId))
                .group(group)
                .isApproved(true)
                .isAdmin(!dto.getGroupType().equals(GroupType.ONE_TO_ONE) && userId.equals(currentUserId))
                .joinDate(LocalDateTime.now())
                .build()).toList();
        members = memberRepository.saveAll(members);
        if (!dto.getGroupType().equals(GroupType.ONE_TO_ONE)) {
            var groupId = members.get(0).getGroup().getId();
            for (Long id : dto.getUserIds()) {
                NotificationData data = NotificationData.builder()
                        .senderId(currentUserId)
                        .recipientId(id)
                        .groupType(group.getGroupType())
                        .groupName(group.getGroupName())
                        .referenceId(groupId)
                        .build();
                notificationService.sendNotification(NotificationType.GROUP_INVITATION, data);
            }
        }
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Created new group", groupMapper.toDto(group)));
    }

    @Override
    @Transactional
    public ResponseEntity<?> inviteToGroup(GroupDto dto) {
        var currentUserId = userService.getCurrentLoggedInUser().getId();

        Group group = groupRepository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundException("Group not found"));

        if (!memberRepository.existsByUser_IdAndGroup_IdAndIsApprovedIsTrue(currentUserId, dto.getId())) {
            throw new BadRequestException("User is not in group");
        }

        if (group.getGroupType().equals(GroupType.FAMILY)) {
            var hasError = dto.getUserIds().stream().anyMatch(userId -> memberRepository.existsByUser_IdAndGroup_GroupType(userId, GroupType.FAMILY));
            if (hasError) {
                throw new BadRequestException("There are members in the list joined family group before!");
            }
        }
        List<Member> newMembers = dto.getUserIds().stream().map(userId -> Member.builder()
                .user(userService.findById(userId))
                .group(group)
                .isApproved(false)
                .isAdmin(false)
                .joinDate(LocalDateTime.now())
                .build()).toList();
        newMembers = memberRepository.saveAll(newMembers);
        var groupId = newMembers.get(0).getGroup().getId();
        for (Long id : dto.getUserIds()) {
            NotificationData data = NotificationData.builder()
                    .senderId(currentUserId)
                    .recipientId(id)
                    .groupName(group.getGroupName())
                    .groupType(group.getGroupType())
                    .referenceId(groupId)
                    .build();
            notificationService.sendNotification(NotificationType.GROUP_INVITATION, data);
        }
        //send noti here
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Invited", ""));
    }

    @Override
    public ResponseEntity<?> getMembers(Long groupId, boolean isApproved) {
        var currentUserId = userService.getCurrentLoggedInUser().getId();

        if (!groupRepository.existsById(groupId)) {
            throw new BadRequestException("Group not found");
        }

        if (!memberRepository.existsByUser_IdAndGroup_IdAndIsApprovedIsTrue(currentUserId, groupId)) {
            throw new BadRequestException("User is not in group");
        }
        List<Member> members = memberRepository.getAllMembers(groupId, isApproved);
        List<MemberDto> dtos = members.stream().map(member -> {
            MemberDto dto = MemberDto.builder()
                    .id(member.getId())
                    .isAdmin(member.isAdmin())
                    .isApproved(member.isApproved())
                    .group(groupMapper.toDto(member.getGroup()))
                    .user(userMapper.toDto(member.getUser()))
                    .joinDate(member.getJoinDate())
                    .build();
            if (GroupType.FAMILY.equals(member.getGroup().getGroupType())) {
                FamilyRelationship relationship = relationshipService.getFamilyRelationshipBetweenTwoPeople(currentUserId, member.getUser().getId());
                if (!Objects.isNull(relationship) && currentUserId.equals(relationship.getSender().getId())) {
                    dto.setRole(relationship.getReceiverRole().toString());
                    dto.setFamilyType(relationship.getFamilyType());
                }
            } else {
                FriendRelationship friendRelationship = relationshipService.getFriendRelationshipBetweenTwoPeople(currentUserId, member.getUser().getId());
                if (!Objects.isNull(friendRelationship)) {
                    dto.setFriendType(friendRelationship.getFriendType());
                }
            }
            return dto;
        }).toList();

        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Get members", dtos));
    }

    @Override
    @Transactional
    public ResponseEntity<?> kickMember(Long groupId, Long userId) {
        var currentUserId = userService.getCurrentLoggedInUser().getId();
        if (!memberRepository.existsByUser_IdAndGroup_IdAndIsAdmin(currentUserId, groupId, true)) {
            throw new BadRequestException("Unable to implement");
        }

        Member member = memberRepository.getMemberByUser_IdAndGroup_Id(userId, groupId)
                .orElseThrow(() -> new NotFoundException("Member not found"));

        memberRepository.deleteById(member.getId());
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Kick member successfully", ""));
    }

    @Override
    @Transactional
    public ResponseEntity<?> approveMember(Long groupId, Long userId) {
        var currentUserId = userService.getCurrentLoggedInUser().getId();
        if (!memberRepository.existsByUser_IdAndGroup_IdAndIsAdmin(currentUserId, groupId, true)) {
            throw new BadRequestException("Unable to approve member");
        }

        Member member = memberRepository.getMemberByUser_IdAndGroup_Id(userId, groupId)
                .orElseThrow(() -> new NotFoundException("Member or group not found"));
        member.setApproved(true);
        memberRepository.save(member);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Approved Member", ""));
    }

    @Override
    public ResponseEntity<?> getUserGroups() {
        var currentUserId = userService.getCurrentLoggedInUser().getId();
        var buddies = groupRepository.findGroupsByUserAndGroupType(currentUserId, GroupType.ONE_TO_ONE).stream().map(group -> {
            var user = group.getMembers().stream().filter(m -> !m.getUser().getId().equals(currentUserId)).findFirst().orElseThrow(() -> new NotFoundException("member not found")).getUser();
            if (relationshipRepository.checkIfRelationshipPending(user.getId(), currentUserId)) {
                return null;
            }
            return BuddyGroupDto.builder()
                    .id(group.getId())
                    .userDto(userMapper.toDto(user))
                    .groupType(group.getGroupType())
                    .updatedAt(group.getUpdatedAt())
                    .build();
        }).filter(Objects::nonNull).toList();
        var families = groupRepository.findGroupsByUserAndGroupType(currentUserId, GroupType.FAMILY).stream().map(groupMapper::toDto).toList();
        var friends = groupRepository.findGroupsByUserAndGroupType(currentUserId, GroupType.FRIEND).stream().map(groupMapper::toDto).toList();

        var dto = UserGroupGetDto.builder()
                .buddies(buddies)
                .families(families)
                .friends(friends)
                .build();
//        var groups = groupRepository.findGroupsByUserAndGroupType(currentUserId, groupType);

        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Get user groups", dto));
    }

    @Override
    public Group findGroupById(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Group not found"));
    }

    @Override
    public Member findMemberByUserId(Long id, Long groupId) {
        return memberRepository.getMemberByUser_IdAndGroup_Id(id, groupId)
                .orElseThrow(() -> new NotFoundException("Member not found"));
    }
}
