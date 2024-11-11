package com.mobile.buddybound.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LocationHistoryDto {
    private Long id;
    private Long userId;
    private double latitude;
    private double longitude;
    private LocalDateTime createdAt;
}
