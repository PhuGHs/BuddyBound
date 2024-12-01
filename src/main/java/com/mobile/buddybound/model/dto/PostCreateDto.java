package com.mobile.buddybound.model.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PostCreateDto {
    private String note;
    private List<Long> viewerIds;
    private Long groupId;
    private LocationHistoryDto location;
}
