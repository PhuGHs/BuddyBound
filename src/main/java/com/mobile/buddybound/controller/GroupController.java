package com.mobile.buddybound.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mobile.buddybound.model.dto.GroupDto;
import com.mobile.buddybound.model.dto.GroupMemberDto;
import com.mobile.buddybound.model.dto.Views;
import com.mobile.buddybound.service.GroupService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/group")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class GroupController {
    private final GroupService groupService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADULTS')")
    @JsonView({Views.Read.class})
    public ResponseEntity<?> createGroup(@RequestBody @JsonView({Views.Create.class}) GroupDto group) {
        return groupService.createGroup(group);
    }

    @GetMapping("/getMembers")
    @JsonView({Views.Read.class})
    public ResponseEntity<?> getGroupMembers(
            @RequestParam(name = "groupId") Long groupId,
            @RequestParam(name = "isApproved") boolean isApproved
    ) {
        return groupService.getMembers(groupId, isApproved);
    }

    @PutMapping("/invite")
    @PreAuthorize("hasAuthority('ADULTS')")
    @JsonView({Views.Read.class})
    public ResponseEntity<?> inviteToGroup(@RequestBody @JsonView(Views.Update.class) GroupDto group) {
        return groupService.inviteToGroup(group);
    }

    @PutMapping("/members/approve")
    @PreAuthorize("hasAuthority('ADULTS')")
    @JsonView({Views.Read.class})
    public ResponseEntity<?> approveMember(@RequestBody @JsonView(Views.Update.class) GroupMemberDto dto) {
        return groupService.approveMember(dto.getGroupId(), dto.getUserId());
    }

    @PutMapping("/members/kick")
    @PreAuthorize("hasAuthority('ADULTS')")
    @JsonView({Views.Read.class})
    public ResponseEntity<?> kickMember(@RequestBody @JsonView(Views.Update.class) GroupMemberDto dto) {
        return groupService.kickMember(dto.getGroupId(), dto.getUserId());
    }

    @PutMapping("/members/reject")
    @PreAuthorize("hasAuthority('ADULTS')")
    @JsonView({Views.Read.class})
    public ResponseEntity<?> rejectMember(@RequestBody @JsonView(Views.Update.class) GroupMemberDto dto) {
        return groupService.kickMember(dto.getGroupId(), dto.getUserId());
    }
}
