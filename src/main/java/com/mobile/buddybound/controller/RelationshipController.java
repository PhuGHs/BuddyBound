package com.mobile.buddybound.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mobile.buddybound.model.dto.RelationshipDto;
import com.mobile.buddybound.model.enumeration.FamilyType;
import com.mobile.buddybound.model.enumeration.RelationshipType;
import com.mobile.buddybound.service.RelationshipService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
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
    public ResponseEntity<?> addRelationship(@RequestBody RelationshipDto dto) {
        return relationshipService.addRelationship(dto);
    }

    @GetMapping("/get-all-relationship")
    public ResponseEntity<?> getAllFamilyRelationship(@RequestParam("isPending") boolean isPending, @RequestParam("type") RelationshipType type) {
        return this.relationshipService.getAllRelationship(isPending, type);
    }
}
