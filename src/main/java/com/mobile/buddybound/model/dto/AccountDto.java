package com.mobile.buddybound.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountDto {
    private Long id;
    private String email;
    private String password;
    private UserDto user;
    private RoleDto role;
    private String verificationCode;
    private String refreshToken;
}
