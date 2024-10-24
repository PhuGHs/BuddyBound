package com.mobile.buddybound.service;

import com.mobile.buddybound.model.dto.AccountDto;
import com.mobile.buddybound.model.dto.LoginDto;
import com.mobile.buddybound.model.dto.RegisterDto;
import com.mobile.buddybound.model.response.ApiResponse;
import com.mobile.buddybound.model.response.AuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    ResponseEntity<ApiResponse> register(RegisterDto registerDto);
    ResponseEntity<AuthResponse> login(LoginDto loginDto);
    ResponseEntity<ApiResponse> refresh(String refreshToken);
}
