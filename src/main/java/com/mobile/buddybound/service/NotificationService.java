package com.mobile.buddybound.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface NotificationService {
    ResponseEntity<?> getNotifications();
    ResponseEntity<?> markAllAsRead();
    ResponseEntity<?> markAsRead(Long notificationId);
    ResponseEntity<?> deleteNotification(Long notificationId);
}
