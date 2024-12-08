package com.mobile.buddybound.controller;

import com.mobile.buddybound.model.enumeration.NotificationType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/notifications")
public class NotificationController {
    @GetMapping
    public ResponseEntity<?> getNotifications(@RequestParam(value = "type", required = false) NotificationType type) {
        return ResponseEntity.ok("");
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
        return ResponseEntity.ok("");
    }
}
