package com.mobile.buddybound.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PostVisibilityGetDto {
    @JsonView(Views.Read.class)
    private Long id;

    @JsonView(Views.Read.class)
    private MemberPostDto member;
}
