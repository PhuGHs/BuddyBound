package com.mobile.buddybound.pattern.strategy.auth_strategy;

import com.mobile.buddybound.model.dto.LoginDto;
import jakarta.security.auth.message.AuthException;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Setter
public class AuthContext {
    private AuthStrategy strategy;

    public ResponseEntity<?> authenticate(LoginDto dto) {
        if (Objects.isNull(strategy)) {
            throw new IllegalStateException("Strategy not set");
        }
        return strategy.authenticate(dto);
    }
}
