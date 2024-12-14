package com.mobile.buddybound.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mobile.buddybound.model.dto.BlockedRelationshipDto;
import com.mobile.buddybound.model.dto.RelationshipDto;
import com.mobile.buddybound.model.dto.Views;
import com.mobile.buddybound.model.entity.FamilyRelationship;
import com.mobile.buddybound.model.entity.FriendRelationship;
import com.mobile.buddybound.model.entity.Relationship;
import com.mobile.buddybound.model.enumeration.RelationshipType;
import com.mobile.buddybound.model.response.ApiResponse;
import com.mobile.buddybound.model.response.ApiResponseStatus;
import com.mobile.buddybound.service.RelationshipService;
import com.mobile.buddybound.service.UserService;
import com.mobile.buddybound.service.mapper.RelationshipMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/relationship")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class RelationshipController {
    private final RelationshipService relationshipService;
    private final UserService userService;
    private final RelationshipMapper relationshipMapper;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADULTS')")
    @JsonView(Views.Read.class)
    public ResponseEntity<?> addRelationship(@JsonView(Views.Create.class) @RequestBody RelationshipDto dto) {
        Object relationship = relationshipService.addRelationship(dto);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Add relationship", relationship));
    }

    @GetMapping("/get-user-relationship")
    @JsonView(Views.Read.class)
    public ResponseEntity<?> getAllFamilyRelationship(
            @RequestParam(value = "searchText", required = false) String searchText,
            @RequestParam(value = "isPending", defaultValue = "false", required = false) boolean isPending,
            @RequestParam("type") RelationshipType type
    ) {
        var currentUserId = userService.getCurrentLoggedInUser().getId();
        List<RelationshipDto> dtoList = this.relationshipService.getAllRelationship(currentUserId, searchText, isPending, type);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Get relationship", dtoList));
    }

    @PutMapping("/update-relationship")
    @JsonView({Views.Read.class})
    @PreAuthorize("hasAuthority('ADULTS')")
    public ResponseEntity<?> updateRelationship(@JsonView(Views.Update.class) @RequestBody RelationshipDto dto) {
        Relationship relationship = relationshipService.updateRelationship(dto);
        if (relationship instanceof FamilyRelationship) {
            return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "update Relationship", relationshipMapper.toFamilyRelationshipDto((FamilyRelationship) relationship)));
        }
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "update relationship", relationshipMapper.toFriendRelationshipDto((FriendRelationship) relationship)));
    }

    @PostMapping("/update-restriction")
    @PreAuthorize("hasAuthority('ADULTS')")
    @JsonView({Views.Read.class})
    public ResponseEntity<?> restrict(@JsonView(Views.Update.class) @RequestBody BlockedRelationshipDto dto) {
        return this.relationshipService.limitOrUnlimitRelationship(dto);
    }

    @GetMapping("/get-all-restricted-user")
    @PreAuthorize("hasAuthority('ADULTS')")
    @JsonView(Views.Read.class)
    public ResponseEntity<?> getAllRestrictedUser() {
        return this.relationshipService.getUserLimitedRelationshipList();
    }

    @PutMapping("/accept-invitation/{relationshipId}")
    public ResponseEntity<?> acceptInvitation(@PathVariable Long relationshipId) {
        return relationshipService.acceptRelationship(relationshipId);
    }
}
