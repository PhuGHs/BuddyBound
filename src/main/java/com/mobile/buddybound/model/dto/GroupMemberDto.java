package com.mobile.buddybound.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GroupMemberDto {
    @JsonView({Views.Update.class})
    private Long groupId;

    @JsonView({Views.Update.class})
    private Long userId;
}
