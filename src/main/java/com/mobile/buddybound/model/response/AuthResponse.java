package com.mobile.buddybound.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mobile.buddybound.model.dto.AccountDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthResponse {
    @JsonProperty("account")
    private AccountDto account;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;
}
