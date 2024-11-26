package com.mobile.buddybound.service.impl;

import com.mobile.buddybound.exception.BadRequestException;
import com.mobile.buddybound.model.dto.GroupDto;
import com.mobile.buddybound.model.entity.Group;
import com.mobile.buddybound.model.entity.Member;
import com.mobile.buddybound.model.entity.User;
import com.mobile.buddybound.model.enumeration.GroupType;
import com.mobile.buddybound.model.response.ApiResponse;
import com.mobile.buddybound.model.response.ApiResponseStatus;
import com.mobile.buddybound.repository.GroupRepository;
import com.mobile.buddybound.repository.MemberRepository;
import com.mobile.buddybound.service.GroupService;
import com.mobile.buddybound.service.UserService;
import com.mobile.buddybound.service.mapper.GroupMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

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

    @Override
    public ResponseEntity<?> createGroup(GroupDto dto) {
        Long currentUserId = userService.getCurrentLoggedInUser().getId();

        if (dto.getGroupType().equals(GroupType.FAMILY)) {
            if (memberRepository.existsByUser_IdAndGroup_GroupType(currentUserId, GroupType.FAMILY)) {
                throw new BadRequestException("A user can only create or join exactly one family group.");
            }
        }

        Group group = groupRepository.save(groupMapper.toEntity(dto));
        List<Long> userIds = Stream.concat(Stream.of(currentUserId), dto.getUserIds().stream()).toList();
        List<Member> members = userIds.stream().map(userId -> {
            return Member.builder()
                    .user(userService.findById(userId))
                    .group(group)
                    .isApproved(true)
                    .isAdmin(!dto.getGroupType().equals(GroupType.ONE_TO_ONE) && userId.equals(currentUserId))
                    .joinDate(LocalDateTime.now())
                    .build();
        }).toList();
        memberRepository.saveAll(members);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Created new group", groupMapper.toDto(group)));
    }
}
