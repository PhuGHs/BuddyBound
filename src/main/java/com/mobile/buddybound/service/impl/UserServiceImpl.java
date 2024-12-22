package com.mobile.buddybound.service.impl;

import com.mobile.buddybound.exception.NotFoundException;
import com.mobile.buddybound.model.dto.RegisterDto;
import com.mobile.buddybound.model.dto.SettingDto;
import com.mobile.buddybound.model.dto.UpdateProfileDto;
import com.mobile.buddybound.model.entity.Account;
import com.mobile.buddybound.model.entity.User;
import com.mobile.buddybound.model.entity.UserSettings;
import com.mobile.buddybound.model.response.ApiResponse;
import com.mobile.buddybound.model.response.ApiResponseStatus;
import com.mobile.buddybound.repository.AccountRepository;
import com.mobile.buddybound.repository.RelationshipRepository;
import com.mobile.buddybound.repository.SettingRepository;
import com.mobile.buddybound.repository.UserRepository;
import com.mobile.buddybound.service.UserService;
import com.mobile.buddybound.service.mapper.SettingMapper;
import com.mobile.buddybound.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class UserServiceImpl implements UserService  {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final UserMapper userMapper;
    private final SettingMapper settingMapper;
    private final SettingRepository settingRepository;
    private final RelationshipRepository relationshipRepository;
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
    public ResponseEntity<?> searchUser(String fullName, String phoneNumber, Boolean hasRelationship) {
        User user = getCurrentLoggedInUser();
        List<User> matchedUsers = userRepository.findByFullNameContainingIgnoreCaseOrPhoneNumberContaining(fullName, phoneNumber);
        matchedUsers.removeIf(u -> {
            if (u.getId().equals(user.getId())) {
                return true;
            }
            if (!Objects.isNull(hasRelationship) && hasRelationship) {
                return relationshipRepository.checkIfExist(user.getId(), u.getId());
            }
            return false;
        });
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Matched users: ", matchedUsers.stream().map(userMapper::toDto)));
    }

    @Override
    public ResponseEntity<?> setUserSettings(SettingDto dto) {
        var currentUser = this.getCurrentLoggedInUser();
        var settings = settingRepository.findByUser_Id(currentUser.getId())
                .orElse(null);
        if (Objects.isNull(settings)) {
            UserSettings newSettings = UserSettings.builder()
                    .user(currentUser)
                    .contactEnabled(dto.isContactEnabled())
                    .locationEnabled(dto.isLocationEnabled())
                    .locationHistoryEnabled(dto.isLocationEnabled() && dto.isLocationHistoryEnabled())
                    .build();
            newSettings = settingRepository.save(newSettings);
            return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "set user settings", settingMapper.toDto(newSettings)));
        }

        settings.setContactEnabled(dto.isContactEnabled());
        settings.setLocationEnabled(dto.isLocationEnabled());
        settings.setLocationHistoryEnabled(dto.isLocationEnabled() && dto.isLocationHistoryEnabled());
        settings = settingRepository.save(settings);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "set user settings", settingMapper.toDto(settings)));
    }

    @Override
    public ResponseEntity<?> getUserSettings() {
        var currentUser = this.getCurrentLoggedInUser();
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Get user settings", Objects.isNull(currentUser.getSettings()) ? "" : settingMapper.toDto(currentUser.getSettings())));
    }

    @Override
    public ResponseEntity<?> updateProfile(UpdateProfileDto dto) {
        var user = this.findById(dto.getUserId());
        user.setBirthday(dto.getBirthday());
        user.setFullName(dto.getFullName());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setGender(dto.isGender());

        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Update profile", userMapper.toDto(userRepository.save(user))));
    }
}
