package com.mobile.buddybound.controller;

import com.mobile.buddybound.service.LocationHistoryService;
import com.mobile.buddybound.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("${api.prefix}/location-history")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class LocationHistoryController {
    private final LocationHistoryService locationHistoryService;

    @GetMapping("/get-location")
    @Operation(
            summary = "Get user location history",
            description = "Retrieves location history for the currently logged-in user within the specified date range"
    )
    public ResponseEntity<?> getLocationHistory(
            @Parameter(
                    description = "Start date in format YYYY-MM-DD (e.g., 2024-12-12)",
                    example = "2024-12-12"
            )
            @RequestParam(value = "startDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @Parameter(
                    description = "End date in format YYYY-MM-DD (e.g., 2024-12-12)",
                    example = "2024-12-12"
            )
            @RequestParam(value = "endDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return locationHistoryService.getUserLocationHistory(startDate, endDate);
    }
}
