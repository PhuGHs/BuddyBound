package com.mobile.buddybound.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
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
    private boolean isAdmin;

    @JsonView({ Views.Read.class })
    private boolean isApproved;
}
