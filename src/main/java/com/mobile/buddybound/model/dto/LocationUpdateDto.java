package com.mobile.buddybound.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LocationUpdateDto {
    @JsonView(Views.Read.class)
    private LocationDto location;

    @JsonView(Views.Read.class)
    private List<Long> blockedIds;
}
