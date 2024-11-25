package com.mobile.buddybound.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MemberDto {
    private Long id;
    private UserDto user;
    private GroupDto group;
    private LocalDateTime joinDate = LocalDateTime.now();
    private boolean isAdmin;
    private boolean isApproved;
}
