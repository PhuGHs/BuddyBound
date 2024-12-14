package com.mobile.buddybound.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mobile.buddybound.model.dto.LocationDto;
import com.mobile.buddybound.model.dto.Views;
import com.mobile.buddybound.service.LocationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/location")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class LocationController {
    private final LocationService locationService;

    @PostMapping("/update")
    public void updateLocation(@RequestBody @JsonView({Views.Create.class}) LocationDto locationDto) {
        locationService.updateUserLocation(locationDto);
    }

    @GetMapping("/loadMap/{groupId}")
    @JsonView(Views.Read.class)
    public ResponseEntity<?> getUserLocationsWithinAGroup(@PathVariable Long groupId) {
        return locationService.loadMap(groupId);
    }
}
