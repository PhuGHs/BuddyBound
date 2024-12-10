package com.mobile.buddybound.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mobile.buddybound.model.dto.SettingDto;
import com.mobile.buddybound.model.dto.Views;
import com.mobile.buddybound.model.entity.UserSettings;
import com.mobile.buddybound.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    private final UserService userService;

    @GetMapping("/search")
    public ResponseEntity<?> searchUsers(
            @RequestParam(required = false, value = "fullName") String fullName,
            @RequestParam(required = false, value = "phoneNumber") String phoneNumber)
    {
        return userService.searchUser(fullName, phoneNumber);
    }

    @GetMapping("/settings")
    public ResponseEntity<?> getUserSettings() {
        return userService.getUserSettings();
    }

    @PutMapping("/update-settings")
    @JsonView(Views.Read.class)
    public ResponseEntity<?> updateSettings(@RequestBody @JsonView({Views.Create.class}) SettingDto dto) {
        return userService.setUserSettings(dto);
    }
}
