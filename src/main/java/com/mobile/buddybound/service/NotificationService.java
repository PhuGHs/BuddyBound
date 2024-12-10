package com.mobile.buddybound.service;

import com.mobile.buddybound.model.dto.NotificationData;
import com.mobile.buddybound.model.enumeration.NotificationType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface NotificationService {
    ResponseEntity<?> getNotifications();
    ResponseEntity<?> markAllAsRead();
    ResponseEntity<?> markAsRead(Long notificationId);
    ResponseEntity<?> deleteNotification(Long notificationId);
    void sendNotification(NotificationType type, NotificationData data);
}
