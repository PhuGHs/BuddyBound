package com.mobile.buddybound.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlockedRelationshipDto {
    @JsonView({Views.Read.class, Views.Update.class})
    private Long id = 0L;

    @JsonView({Views.Read.class})
    private UserDto blockedUser;

    @JsonView({Views.Update.class})
    private Long blockedUserId;
}
