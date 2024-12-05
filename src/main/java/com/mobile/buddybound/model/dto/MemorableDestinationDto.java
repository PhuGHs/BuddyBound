package com.mobile.buddybound.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.mobile.buddybound.model.entity.LocationHistory;
import com.mobile.buddybound.model.enumeration.MemorableDestinationType;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MemorableDestinationDto {
    @JsonView({Views.Read.class, Views.Update.class})
    private Long id;

    @JsonView({Views.Read.class, Views.Create.class, Views.Update.class})
    private String note;

    @JsonView({Views.Read.class, Views.Create.class, Views.Update.class})
    private MemorableDestinationType locationType;

    @JsonView({Views.Read.class, Views.Create.class})
    private LocationHistoryDto location;
}
