package com.mobile.buddybound.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mobile.buddybound.model.dto.RelationshipDto;
import com.mobile.buddybound.service.RelationshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/relationship")
@RequiredArgsConstructor
public class RelationshipController {
    private RelationshipService relationshipService;

    @PostMapping("/add")
    @JsonView(RelationshipDto.CreateView.class)
    public ResponseEntity<?> addRelationship(@RequestBody RelationshipDto dto) {
        return relationshipService.addRelationship(dto);
    }
}
