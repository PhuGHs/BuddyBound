package com.mobile.buddybound.controller;

import com.mobile.buddybound.model.enumeration.NotificationType;
import com.mobile.buddybound.service.NotificationService;
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
    @GetMapping
    public ResponseEntity<?> getNotifications() {
        return notificationService.getNotifications();
    }

    @PutMapping("/mark-all-as-read")
    public ResponseEntity<?> markAllAsRead() {
        return ResponseEntity.ok("");
    }

    @PutMapping("/{notificationId}")
    public ResponseEntity<?> markAsRead(@PathVariable Long notificationId) {
        return ResponseEntity.ok("");
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<?> deleteNotification(@PathVariable Long notificationId) {
        return notificationService.deleteNotification(notificationId);
    }
}
