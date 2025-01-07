package com.mobile.buddybound.pattern.strategy.auth_strategy;

import com.mobile.buddybound.exception.NotFoundException;
import com.mobile.buddybound.model.dto.LoginDto;
import com.mobile.buddybound.model.entity.Account;
import com.mobile.buddybound.model.entity.AccountSession;
import com.mobile.buddybound.model.response.AuthResponse;
import com.mobile.buddybound.repository.AccountRepository;
import com.mobile.buddybound.repository.AccountSessionRepository;
import com.mobile.buddybound.security.JwtTokenUtils;
import com.mobile.buddybound.service.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class TraditionalAuthStrategy implements AuthStrategy {
    private final AuthenticationManager authenticationManager;
    private final AccountRepository accountRepository;
    private final AccountSessionRepository accountSessionRepository;
    private final AccountMapper accountMapper;
    private final JwtTokenUtils jwtTokenUtils;
    @Override
    public ResponseEntity<AuthResponse> authenticate(LoginDto dto) {
        //        authenticate
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
//        getAccount
        Account account = accountRepository.findByEmail(dto.getEmail()).orElseThrow(() -> new NotFoundException("Account is not found"));
//        generateToken
        String accessToken = jwtTokenUtils.generateToken(dto.getEmail());
        String refreshToken = jwtTokenUtils.generateRefreshToken(dto.getEmail());

        accountSessionRepository.updateSessionByAccountId(account.getId());

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
}
