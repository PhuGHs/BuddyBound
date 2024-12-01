package com.mobile.buddybound.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CommentDto {
    private Long id;
    private Long postId;
    private MemberDto member;
    private String content;
    private LocalDateTime createdAt = LocalDateTime.now();
}
