package com.mobile.buddybound.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
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
    @JsonView({ Views.Read.class, Views.Update.class })
    private Long id;

    @JsonView({ Views.Read.class, Views.Create.class, Views.Update.class})
    private String groupName;

    @JsonView({ Views.Read.class, Views.Create.class, Views.Update.class})
    private String groupDescription;

    @JsonView({ Views.Read.class })
    private LocalDateTime createdAt = LocalDateTime.now();

    @JsonView({ Views.Read.class })
    private LocalDateTime updatedAt = LocalDateTime.now();

    @JsonView({ Views.Create.class, Views.Update.class})
    private List<Long> userIds;

    @JsonView({ Views.Read.class, Views.Create.class, Views.Update.class})
    private GroupType groupType;
}
