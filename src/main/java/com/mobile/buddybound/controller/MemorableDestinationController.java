package com.mobile.buddybound.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mobile.buddybound.model.dto.MemorableDestinationDto;
import com.mobile.buddybound.model.dto.Views;
import com.mobile.buddybound.model.enumeration.MemorableDestinationGetType;
import com.mobile.buddybound.model.enumeration.MemorableDestinationType;
import com.mobile.buddybound.model.response.ApiResponse;
import com.mobile.buddybound.model.response.ApiResponseStatus;
import com.mobile.buddybound.service.MemorableDestinationService;
import com.mobile.buddybound.service.UserService;
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
    private final UserService userService;
//    @GetMapping("/get-nearby")
//    @JsonView(Views.Read.class)
//    public ResponseEntity<?> getMemorableDestination(@RequestParam Double latitude, @RequestParam Double longitude, @RequestParam(required = false) MemorableDestinationType type) {
//        return memorableDestinationService.getNearbyDestinations(latitude, longitude, type);
//    }

    @GetMapping
    @JsonView(Views.Read.class)
    public ResponseEntity<?> getAll(@RequestParam(required = false) MemorableDestinationType type) {
        var currentUserId = userService.getCurrentLoggedInUser().getId();
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Get all destinations", memorableDestinationService.getAllDestinations(currentUserId, type)));
    }

    @PostMapping
    @JsonView(Views.Read.class)
    public ResponseEntity<?> createDestination(@JsonView({Views.Create.class}) @RequestBody MemorableDestinationDto dto) {
        return memorableDestinationService.createDestination(dto);
    }

    @PutMapping
    @JsonView(Views.Update.class)
    public ResponseEntity<?> updateDestination(@JsonView({Views.Update.class}) @Valid @RequestBody MemorableDestinationDto dto) {
        return memorableDestinationService.updateDestination(dto);
    }

    @DeleteMapping
    @JsonView(Views.Read.class)
    public ResponseEntity<?> deleteDestination(@RequestParam Long destinationId) {
        return memorableDestinationService.deleteDestination(destinationId);
    }
}
