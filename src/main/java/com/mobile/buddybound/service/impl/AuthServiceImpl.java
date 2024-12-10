package com.mobile.buddybound.service.impl;

import com.mobile.buddybound.exception.BadRequestException;
import com.mobile.buddybound.exception.NotFoundException;
import com.mobile.buddybound.model.dto.LoginDto;
import com.mobile.buddybound.model.dto.RegisterDto;
import com.mobile.buddybound.model.entity.*;
import com.mobile.buddybound.model.response.ApiResponse;
import com.mobile.buddybound.model.response.ApiResponseStatus;
import com.mobile.buddybound.model.response.AuthResponse;
import com.mobile.buddybound.repository.*;
import com.mobile.buddybound.security.JwtTokenUtils;
import com.mobile.buddybound.service.AuthService;
import com.mobile.buddybound.service.MailService;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AccountRepository accountRepository;
    private final AccountSessionRepository accountSessionRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final AccountMapper accountMapper;
    private final UserRepository userRepository;
    private final UserImageRepository userImageRepository;
    private final MailService mailService;
    private final JwtTokenUtils jwtTokenUtils;
    private final static String maleAvatarUrl = "https://ik.imagekit.io/apwerlhez/5.png?updatedAt=1732544514448";
    private final static String femaleAvatarUrl = "https://ik.imagekit.io/apwerlhez/2.png?updatedAt=1732544514560";

    @Override
    @Transactional
    public ResponseEntity<ApiResponse> register(RegisterDto registerDto) {
        if (accountRepository.existsByEmail(registerDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse(ApiResponseStatus.FAIL, "Email address was already taken", ""));
        }

        Role adult = roleRepository.findByRoleName(Role.ADULTS)
                .orElseThrow(() -> new NotFoundException("Can't find role"));

        Role child = roleRepository.findByRoleName(Role.CHILDREN)
                .orElseThrow(() -> new NotFoundException("Can't find role"));

        Image image = registerDto.isGender() ? Image.builder().imageUrl(maleAvatarUrl).build() : Image.builder().imageUrl(femaleAvatarUrl).build();

        User user = User.builder()
                .fullName(registerDto.getFullName())
                .birthday(registerDto.getBirthday())
                .gender(registerDto.isGender())
                .build();

        boolean isChild = Period.between(registerDto.getBirthday(), LocalDate.now()).getYears() < 18;
        user = userRepository.save(user);

        UserImage userImage = UserImage.builder()
                .user(user)
                .image(image)
                .mainAvatar(true)
                .build();
        userImageRepository.save(userImage);

        Account account = Account.builder()
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .role(isChild ? child : adult)
                .user(user)
                .verificationCode("")
                .build();

        account = accountRepository.save(account);

        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS,
                "Register successfully", accountMapper.toDto(account)));
    }

    @Override
    @Transactional
    public ResponseEntity<AuthResponse> login(LoginDto loginDto) {
//        authenticate
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
//        getAccount
        Account account = accountRepository.findByEmail(loginDto.getEmail()).orElseThrow(() -> new NotFoundException("Account is not found"));
//        generateToken
        String accessToken = jwtTokenUtils.generateToken(loginDto.getEmail());
        String refreshToken = jwtTokenUtils.generateRefreshToken(loginDto.getEmail());

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

    @Override
    @Transactional
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

    @Override
    public ResponseEntity<?> forgotPassword(String email) {
        Account account = accountRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Account is not found"));
        mailService.sendOtpEmail(email, account.getUser().getFullName());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> verify(String code) {
        if (accountRepository.existsByVerificationCode(code)) {
            throw new BadRequestException("Mismatch verification code");
        }
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> changePassword(RegisterDto registerDto) {
        Account account = accountRepository.findByEmail(registerDto.getEmail())
                .orElseThrow(() -> new NotFoundException("Account is not found"));

        account.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        account.setVerificationCode("");
        accountRepository.save(account);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Change password successfully!", accountMapper.toDto(account)));
    }
}
