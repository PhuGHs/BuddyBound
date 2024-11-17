package com.mobile.buddybound.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mobile.buddybound.model.dto.BlockedRelationshipDto;
import com.mobile.buddybound.model.dto.RelationshipDto;
import com.mobile.buddybound.model.dto.Views;
import com.mobile.buddybound.model.enumeration.RelationshipType;
import com.mobile.buddybound.service.RelationshipService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/relationship")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class RelationshipController {
    private final RelationshipService relationshipService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADULTS')")
    @JsonView(Views.Read.class)
    public ResponseEntity<?> addRelationship(@JsonView(Views.Create.class) @RequestBody RelationshipDto dto) {
        return relationshipService.addRelationship(dto);
    }

    @GetMapping("/get-user-relationship")
    @JsonView(Views.Read.class)
    public ResponseEntity<?> getAllFamilyRelationship( @RequestParam("isPending") boolean isPending, @RequestParam("type") RelationshipType type) {
        return this.relationshipService.getAllRelationship(isPending, type);
    }

    @PutMapping("/update-relationship")
    @JsonView({Views.Read.class})
    @PreAuthorize("hasAuthority('ADULTS')")
    public ResponseEntity<?> updateRelationship(@JsonView(Views.Update.class) @RequestBody RelationshipDto dto) {
        return this.relationshipService.updateRelationship(dto);
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
}
