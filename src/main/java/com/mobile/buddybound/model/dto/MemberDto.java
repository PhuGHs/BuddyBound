package com.mobile.buddybound.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.mobile.buddybound.model.enumeration.FamilyType;
import com.mobile.buddybound.model.enumeration.FriendType;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MemberDto {
    @JsonView({ Views.Read.class })
    private Long id;
    @JsonView({ Views.Read.class })
    private UserDto user;

    @JsonView({ Views.Read.class })
    private GroupDto group;

    @JsonView({ Views.Read.class })
    private LocalDateTime joinDate = LocalDateTime.now();

    @JsonView({ Views.Read.class })
    @JsonProperty("isAdmin")
    private boolean isAdmin;

    @JsonView({ Views.Read.class })
    @JsonProperty("isApproved")
    private boolean isApproved;

    @JsonView({ Views.Read.class })
    private FamilyType familyType;

    @JsonView({ Views.Read.class })
    private FriendType friendType;

    @JsonView({ Views.Read.class })
    private String role;
}
