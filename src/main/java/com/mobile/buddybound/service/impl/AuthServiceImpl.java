package com.mobile.buddybound.service.impl;

import com.mobile.buddybound.exception.NotFoundException;
import com.mobile.buddybound.model.dto.LoginDto;
import com.mobile.buddybound.model.dto.RegisterDto;
import com.mobile.buddybound.model.entity.Account;
import com.mobile.buddybound.model.entity.AccountSession;
import com.mobile.buddybound.model.response.ApiResponse;
import com.mobile.buddybound.model.response.ApiResponseStatus;
import com.mobile.buddybound.model.response.AuthResponse;
import com.mobile.buddybound.repository.AccountRepository;
import com.mobile.buddybound.repository.AccountSessionRepository;
import com.mobile.buddybound.security.JwtTokenUtils;
import com.mobile.buddybound.service.AuthService;
import com.mobile.buddybound.service.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AccountRepository accountRepository;
    private final AccountSessionRepository accountSessionRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final AccountMapper accountMapper;
    private final JwtTokenUtils jwtTokenUtils;

    @Override
    public ResponseEntity<ApiResponse> register(RegisterDto registerDto) {
        if (accountRepository.existsByEmail(registerDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(ApiResponseStatus.FAIL, "Email address is taken", ""));
        }

        Account account = Account.builder()
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .verificationCode("")
                .build();

        account = accountRepository.save(account);

        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Register successfully", accountMapper.toDto(account)));
    }

    @Override
    public ResponseEntity<AuthResponse> login(LoginDto loginDto) {
//        authenticate
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
//        getAccount
        Account account = accountRepository.findByEmail(loginDto.getEmail()).orElseThrow(() -> new NotFoundException("Account is not found"));
//        generateToken
        String accessToken = jwtTokenUtils.generateToken(loginDto.getEmail());
        String refreshToken = jwtTokenUtils.generateRefreshToken(loginDto.getEmail());

        AccountSession accountSession = AccountSession.builder()
                .account(account)
                .createdAt(LocalDateTime.now())
                .lastUsedAt(LocalDateTime.now())
                .refreshToken(refreshToken)
                .expiresAt(LocalDateTime.now().plusDays(30))
                .isRevoked(false)
                .build();

        accountSessionRepository.save(accountSession);

        return ResponseEntity.ok(new AuthResponse(accountMapper.toDto(account), accessToken, refreshToken));
    }

    @Override
    public ResponseEntity<ApiResponse> refresh(String refreshToken) {
        AccountSession session = accountSessionRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new NotFoundException("Refresh token is not found"));
        if (!session.getIsRevoked()) {
            if (session.getExpiresAt().isAfter(LocalDateTime.now())) {
                String email = session.getAccount().getEmail();
                String newAccessToken = jwtTokenUtils.generateToken(email);
                String newRefreshToken = jwtTokenUtils.generateRefreshToken(email);

                session.setRefreshToken(newRefreshToken);
                session.setLastUsedAt(LocalDateTime.now());
                session.setExpiresAt(LocalDateTime.now().plusDays(30));

                accountSessionRepository.save(session);
                return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Refreshed new token", new AuthResponse(accountMapper.toDto(session.getAccount()), newAccessToken, newRefreshToken)));
            }
            return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.FAIL, "Refresh token is expired", ""));
        }
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.FAIL, "Refresh token invalid", ""));
    }
}
