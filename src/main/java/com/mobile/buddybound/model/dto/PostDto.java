package com.mobile.buddybound.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.mobile.buddybound.model.entity.Comment;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class PostDto {
    @JsonView(Views.Read.class)
    private Long id;

    @JsonView(Views.Read.class)
    private String note;

    @JsonView(Views.Read.class)
    private ImageDto image;

    @JsonView(Views.Read.class)
    private boolean isExpired = false;

    @JsonView(Views.Read.class)
    private MemberDto member;

    @JsonView(Views.Read.class)
    private LocationHistoryDto location;

    @JsonView(Views.Read.class)
    private List<PostVisibilityDto> postVisibilities;

    @JsonView(Views.Read.class)
    private LocalDateTime createdAt = LocalDateTime.now();

    @JsonView(Views.Read.class)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @JsonView(Views.Read.class)
    private Comment firstComment;

    @JsonView(Views.Read.class)
    private int commentCount;
}
