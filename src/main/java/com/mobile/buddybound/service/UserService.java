package com.mobile.buddybound.service;

import com.mobile.buddybound.model.dto.SettingDto;
import com.mobile.buddybound.model.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User findById(Long id);
    User getCurrentLoggedInUser();
    ResponseEntity<?> searchUser(String fullName, String phoneNumber);
    ResponseEntity<?> setUserSettings(SettingDto dto);
    ResponseEntity<?> getUserSettings();
}
