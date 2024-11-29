package com.mobile.buddybound.service;

import com.mobile.buddybound.model.dto.GroupDto;
import com.mobile.buddybound.model.enumeration.GroupType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GroupService {
    ResponseEntity<?> createGroup(GroupDto dto);
    ResponseEntity<?> inviteToGroup(GroupDto dto);
    ResponseEntity<?> getMembers(Long groupId, boolean isApproved);
    ResponseEntity<?> kickMember(Long groupId, Long userId);
    ResponseEntity<?> approveMember(Long groupId, Long userId);
    ResponseEntity<?> getUserGroups(GroupType groupType);
}
