package com.mobile.buddybound.controller;

import com.mobile.buddybound.model.enumeration.NotificationType;
import com.mobile.buddybound.model.response.ApiResponse;
import com.mobile.buddybound.model.response.ApiResponseStatus;
import com.mobile.buddybound.service.NotificationService;
import com.mobile.buddybound.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/notifications")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class NotificationController {
    private final NotificationService notificationService;
    private final UserService userService;
    @GetMapping
    public ResponseEntity<?> getNotifications() {
        var currentUserId = userService.getCurrentLoggedInUser().getId();
        var dtoList = notificationService.getNotifications(currentUserId);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Get notifications", dtoList));
    }

    @PutMapping("/mark-all-as-read")
    public ResponseEntity<?> markAllAsRead() {
        var currentUserId = userService.getCurrentLoggedInUser().getId();
        notificationService.markAllAsRead(currentUserId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{notificationId}")
    public ResponseEntity<?> markAsRead(@PathVariable Long notificationId) {
        var currentUserId = userService.getCurrentLoggedInUser().getId();
        notificationService.markAsRead(currentUserId, notificationId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<?> deleteNotification(@PathVariable Long notificationId) {
        notificationService.deleteNotification(notificationId);
        return ResponseEntity.notFound().build();
    }
}
