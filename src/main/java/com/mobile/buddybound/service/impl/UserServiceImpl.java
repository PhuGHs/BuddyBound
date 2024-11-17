package com.mobile.buddybound.service.impl;

import com.mobile.buddybound.exception.NotFoundException;
import com.mobile.buddybound.model.entity.Account;
import com.mobile.buddybound.model.entity.User;
import com.mobile.buddybound.model.response.ApiResponse;
import com.mobile.buddybound.model.response.ApiResponseStatus;
import com.mobile.buddybound.repository.AccountRepository;
import com.mobile.buddybound.repository.UserRepository;
import com.mobile.buddybound.service.UserService;
import com.mobile.buddybound.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Component
public class UserServiceImpl implements UserService  {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final UserMapper userMapper;
    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public User getCurrentLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) auth.getPrincipal();

        Account account = accountRepository.findByEmail(principal.getUsername())
                .orElseThrow(() -> new NotFoundException("Account not found"));
        return account.getUser();
    }

    @Override
    public ResponseEntity<?> searchUser(String fullName, String phoneNumber) {
        User user = getCurrentLoggedInUser();
        List<User> matchedUsers = userRepository.findByFullNameContainingIgnoreCaseOrPhoneNumberContaining(fullName, phoneNumber);
        matchedUsers.removeIf(u -> u.getId().equals(user.getId()));
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Matched users: ", matchedUsers.stream().map(userMapper::toDto)));
    }
}
