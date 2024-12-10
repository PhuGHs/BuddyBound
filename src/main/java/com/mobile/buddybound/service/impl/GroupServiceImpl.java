package com.mobile.buddybound.service.impl;

import com.mobile.buddybound.exception.BadRequestException;
import com.mobile.buddybound.exception.NotFoundException;
import com.mobile.buddybound.model.dto.BuddyGroupDto;
import com.mobile.buddybound.model.dto.GroupDto;
import com.mobile.buddybound.model.dto.NotificationData;
import com.mobile.buddybound.model.dto.UserGroupGetDto;
import com.mobile.buddybound.model.entity.Group;
import com.mobile.buddybound.model.entity.Member;
import com.mobile.buddybound.model.enumeration.GroupType;
import com.mobile.buddybound.model.enumeration.NotificationType;
import com.mobile.buddybound.model.response.ApiResponse;
import com.mobile.buddybound.model.response.ApiResponseStatus;
import com.mobile.buddybound.repository.GroupRepository;
import com.mobile.buddybound.repository.MemberRepository;
import com.mobile.buddybound.service.GroupService;
import com.mobile.buddybound.service.NotificationService;
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
        var groupId = members.get(0).getGroup().getId();
        for (Long id : dto.getUserIds()) {
            NotificationData data = NotificationData.builder()
                    .senderId(currentUserId)
                    .recipientId(id)
                    .groupName(group.getGroupName())
                    .referenceId(groupId)
                    .build();
            notificationService.sendNotification(NotificationType.GROUP_INVITATION, data);
        }
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Created new group", groupMapper.toDto(group)));
    }

    @Override
    @Transactional
    public ResponseEntity<?> inviteToGroup(GroupDto dto) {
        var currentUserId = userService.getCurrentLoggedInUser().getId();

        Group group = groupRepository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundException("Group not found"));

        if (!memberRepository.existsByUser_IdAndGroup_Id(currentUserId, dto.getId())) {
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

        if (!memberRepository.existsByUser_IdAndGroup_Id(currentUserId, groupId)) {
            throw new BadRequestException("User is not in group");
        }

        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Get members", memberRepository.getAllMembers(groupId, isApproved).stream().map(memberMapper::toDto)));
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
            return BuddyGroupDto.builder()
                    .id(group.getId())
                    .userDto(userMapper.toDto(user))
                    .groupType(group.getGroupType())
                    .updatedAt(group.getUpdatedAt())
                    .build();
        }).toList();
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
