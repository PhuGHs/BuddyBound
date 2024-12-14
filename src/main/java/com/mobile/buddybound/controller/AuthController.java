package com.mobile.buddybound.controller;

import com.mobile.buddybound.model.dto.CodeVerificationRequest;
import com.mobile.buddybound.model.dto.ForgotPasswordRequest;
import com.mobile.buddybound.model.dto.LoginDto;
import com.mobile.buddybound.model.dto.RegisterDto;
import com.mobile.buddybound.model.response.ApiResponse;
import com.mobile.buddybound.model.response.AuthResponse;
import com.mobile.buddybound.service.AuthService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterDto registerDto) {
        return authService.register(registerDto);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginDto loginDto) {
        return authService.login(loginDto);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse> refresh(@RequestBody String refreshToken) {
        return authService.refresh(refreshToken);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody @Valid ForgotPasswordRequest forgotPasswordRequest) {
        return authService.forgotPassword(forgotPasswordRequest.getEmail());
    }

    @PostMapping("/verify-code")
    public ResponseEntity<?> verifyCode(
            @Valid @RequestBody CodeVerificationRequest code
            ) {
        return authService.verify(code.getEmail(), code.getCode());
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody @Valid RegisterDto dto) {
        return authService.changePassword(dto);
    }
}
