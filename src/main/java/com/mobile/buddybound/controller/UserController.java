package com.mobile.buddybound.controller;

import com.mobile.buddybound.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
