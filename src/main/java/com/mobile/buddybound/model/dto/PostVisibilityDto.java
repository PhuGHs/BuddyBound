package com.mobile.buddybound.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PostVisibilityDto {
    @JsonView({Views.Read.class})
    private Long id;

    @JsonView({Views.Create.class})
    private Long memberId;

    @JsonView({Views.Read.class})
    private MemberDto viewer;

    @JsonView({Views.Create.class})
    private Long postId;
}
