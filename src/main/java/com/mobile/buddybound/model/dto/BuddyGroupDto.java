package com.mobile.buddybound.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.mobile.buddybound.model.entity.User;
import com.mobile.buddybound.model.enumeration.GroupType;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BuddyGroupDto {
    @JsonView(Views.Read.class)
    private Long id;

    @JsonView(Views.Read.class)
    private GroupType groupType;

    @JsonView(Views.Read.class)
    private LocalDateTime updatedAt;

    @JsonView(Views.Read.class)
    private UserDto userDto;
}
