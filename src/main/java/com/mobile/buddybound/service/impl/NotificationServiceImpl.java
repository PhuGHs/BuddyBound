package com.mobile.buddybound.service.impl;

import com.mobile.buddybound.exception.NotFoundException;
import com.mobile.buddybound.model.dto.NotificationData;
import com.mobile.buddybound.model.dto.NotificationDto;
import com.mobile.buddybound.model.entity.Notification;
import com.mobile.buddybound.model.enumeration.NotificationType;
import com.mobile.buddybound.model.response.ApiResponse;
import com.mobile.buddybound.model.response.ApiResponseStatus;
import com.mobile.buddybound.pattern.factory_method.NotificationFactory;
import com.mobile.buddybound.pattern.factory_method.NotificationFactoryProvider;
import com.mobile.buddybound.repository.NotificationRepository;
import com.mobile.buddybound.service.NotificationService;
import com.mobile.buddybound.service.UserService;
import com.mobile.buddybound.service.WebsocketService;
import com.mobile.buddybound.service.mapper.NotificationMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserService userService;
    private final NotificationMapper notificationMapper;
    private final NotificationFactoryProvider factoryProvider;
    private final WebsocketService websocketService;

    @Override
    @Cacheable(value = "notifications", key = "#currentUserId")
    public List<NotificationDto> getNotifications(Long currentUserId) {
        return notificationRepository.findNotificationByRecipient_IdOrderByCreatedAtDesc(currentUserId)
                .stream()
                .map(t -> switch (t.getNotificationType()) {
                    case COMMENT -> notificationMapper.toCommentNotificationDto(t);
                    case GROUP_POST -> notificationMapper.toGroupPostNotificationDto(t);
                    case GROUP_INVITATION -> notificationMapper.toGroupInvitationNotificationDto(t);
                    case RELATIONSHIP_REQUEST -> notificationMapper.toRelationshipRequest(t);
                })
                .toList();
    }

    @Override
    @CacheEvict(value = "notifications", key = "#currentUserId")
    public void markAllAsRead(Long currentUserId) {
        List<Notification> notifications = notificationRepository.findNotificationByRecipient_IdOrderByCreatedAtDesc(currentUserId)
                .stream()
                .map(n -> {
                    n.setRead(true);
                    return n;
                }).toList();
    }

    @Override
    @CacheEvict(value = "notifications", key = "#currentUserId")
    public void markAsRead(Long currentUserId, Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotFoundException("Notification not found"));
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    @Override
    public void deleteNotification(Long notificationId) {
        if (notificationRepository.existsById(notificationId)) {
            throw new NotFoundException("Notification with id " + notificationId + " not found");
        }
        notificationRepository.deleteById(notificationId);
    }

    @Override
    public void sendNotification(NotificationType type, NotificationData data) {
        NotificationFactory factory = factoryProvider.getFactory(type);
        factory.createNotification(type, data);
        //send websocket
        websocketService.sendNotificationUpdate(data);
    }
}
