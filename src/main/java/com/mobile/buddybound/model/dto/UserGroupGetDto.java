package com.mobile.buddybound.model.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserGroupGetDto {
    private List<GroupDto> buddies;
    private List<GroupDto> families;
    private List<GroupDto> friends;
}
