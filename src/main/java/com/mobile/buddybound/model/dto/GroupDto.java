package com.mobile.buddybound.model.dto;

import com.mobile.buddybound.model.enumeration.GroupType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class GroupDto {
    private Long id;
    private String groupName;
    private String groupDescription;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
    private List<Long> userIds;
    private GroupType groupType;
}
