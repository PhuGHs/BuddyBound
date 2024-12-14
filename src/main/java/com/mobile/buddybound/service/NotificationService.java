package com.mobile.buddybound.service;

import com.mobile.buddybound.model.dto.NotificationData;
import com.mobile.buddybound.model.dto.NotificationDto;
import com.mobile.buddybound.model.enumeration.NotificationType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NotificationService {
    List<NotificationDto> getNotifications(Long currentUserId);
    void markAllAsRead(Long currentUserId);
    void markAsRead(Long currentUserId, Long notificationId);
    void deleteNotification(Long notificationId);
    void sendNotification(NotificationType type, NotificationData data);
}
