package com.mobile.buddybound.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserGroupGetDto {
    @JsonView(Views.Read.class)
    private List<BuddyGroupDto> buddies;

    @JsonView(Views.Read.class)
    private List<GroupDto> families;

    @JsonView(Views.Read.class)
    private List<GroupDto> friends;
}
