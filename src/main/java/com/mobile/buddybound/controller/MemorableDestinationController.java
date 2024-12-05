package com.mobile.buddybound.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mobile.buddybound.model.dto.MemorableDestinationDto;
import com.mobile.buddybound.model.dto.Views;
import com.mobile.buddybound.model.enumeration.MemorableDestinationGetType;
import com.mobile.buddybound.model.enumeration.MemorableDestinationType;
import com.mobile.buddybound.service.MemorableDestinationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/memorable-destination")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class MemorableDestinationController {
    private final MemorableDestinationService memorableDestinationService;
    @GetMapping()
    @JsonView(Views.Read.class)
    public ResponseEntity<?> getMemorableDestination(@RequestParam Double latitude, @RequestParam Double longitude, @RequestParam MemorableDestinationType type) {
        return memorableDestinationService.getNearbyDestinations(latitude, longitude, type);
    }

    @PostMapping()
    @JsonView(Views.Read.class)
    public ResponseEntity<?> createDestination(@JsonView(Views.Create.class) @RequestBody @Valid MemorableDestinationDto dto) {
        return memorableDestinationService.createDestination(dto);
    }

    @DeleteMapping("")
    @JsonView(Views.Read.class)
    public ResponseEntity<?> deleteDestination(@RequestParam @NotNull Long id) {
        return memorableDestinationService.deleteDestination(id);
    }
}
