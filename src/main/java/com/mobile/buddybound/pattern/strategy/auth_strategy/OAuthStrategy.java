package com.mobile.buddybound.pattern.strategy.auth_strategy;

import com.mobile.buddybound.model.dto.LoginDto;
import com.mobile.buddybound.model.response.AuthResponse;
import org.springframework.http.ResponseEntity;

public class OAuthStrategy implements AuthStrategy {
    @Override
    public ResponseEntity<AuthResponse> authenticate(LoginDto dto) {
        return null;
    }
}
