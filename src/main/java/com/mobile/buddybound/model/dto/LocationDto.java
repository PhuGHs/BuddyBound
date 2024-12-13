package com.mobile.buddybound.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LocationDto {
    @JsonView(Views.Read.class)
    private Long id;

    @JsonView(Views.Read.class)
    private Long userId;

    @JsonView({Views.Read.class, Views.Create.class})
    private double latitude;

    @JsonView({Views.Read.class, Views.Create.class})
    private double longitude;

    @JsonView(Views.Read.class)
    private LocalDateTime timestamp;

}
