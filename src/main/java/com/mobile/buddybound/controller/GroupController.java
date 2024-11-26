package com.mobile.buddybound.controller;

import com.mobile.buddybound.model.dto.GroupDto;
import com.mobile.buddybound.service.GroupService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/group")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class GroupController {
    private final GroupService groupService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADULTS')")
    public ResponseEntity<?> createGroup(@RequestBody GroupDto group) {
        return groupService.createGroup(group);
    }
}
