package com.mobile.buddybound.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AccessRequest {
    private Long groupId;
    private Long postId;
    private Long ownerId;
    private Long viewerId;
}
