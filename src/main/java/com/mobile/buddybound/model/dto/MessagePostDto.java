package com.mobile.buddybound.model.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class MessagePostDto {
    private Long groupId;
    private String content;
}
