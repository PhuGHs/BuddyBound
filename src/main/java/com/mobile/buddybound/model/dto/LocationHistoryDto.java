package com.mobile.buddybound.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
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
    @JsonView(Views.Read.class)
    private Long id;

    @JsonView({ Views.Read.class, Views.Create.class })
    private Long userId;

    @JsonView({ Views.Read.class, Views.Create.class })
    private double latitude;

    @JsonView({ Views.Read.class, Views.Create.class })
    private double longitude;

    @JsonView({ Views.Read.class })
    private LocalDateTime createdAt;
}
