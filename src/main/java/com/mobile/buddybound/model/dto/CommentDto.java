package com.mobile.buddybound.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CommentDto {
    @JsonView({Views.Read.class, Views.Update.class})
    private Long id;

    @JsonView({Views.Read.class, Views.Create.class})
    private Long postId;

    @JsonView({Views.Read.class})
    private MemberDto member;

    @JsonView({Views.Read.class, Views.Update.class, Views.Create.class})
    private String content;

    @JsonView({ Views.Read.class })
    private LocalDateTime createdAt = LocalDateTime.now();
}
