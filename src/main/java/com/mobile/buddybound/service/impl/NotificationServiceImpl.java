package com.mobile.buddybound.service.impl;

import com.mobile.buddybound.exception.NotFoundException;
import com.mobile.buddybound.model.dto.NotificationDto;
import com.mobile.buddybound.model.response.ApiResponse;
import com.mobile.buddybound.model.response.ApiResponseStatus;
import com.mobile.buddybound.repository.NotificationRepository;
import com.mobile.buddybound.service.NotificationService;
import com.mobile.buddybound.service.UserService;
import com.mobile.buddybound.service.mapper.NotificationMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
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

    @Override
    public ResponseEntity<?> getNotifications() {
        var currentUserId = userService.getCurrentLoggedInUser().getId();
        List<NotificationDto> dtoList = notificationRepository.findNotificationBySender_IdOrRecipient_IdOrderByCreatedAtDesc(currentUserId, currentUserId)
                .stream()
                .map(t -> switch (t.getNotificationType()) {
                    case COMMENT -> notificationMapper.toCommentNotificationDto(t);
                    case GROUP_POST -> notificationMapper.toGroupPostNotificationDto(t);
                    case GROUP_INVITATION -> notificationMapper.toGroupInvitationNotificationDto(t);
                    case RELATIONSHIP_REQUEST -> notificationMapper.toRelationshipRequest(t);
                })
                .toList();
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Get all notifications", dtoList));
    }

    @Override
    public ResponseEntity<?> markAllAsRead() {
        return null;
    }

    @Override
    public ResponseEntity<?> markAsRead(Long notificationId) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteNotification(Long notificationId) {
        if (notificationRepository.existsById(notificationId)) {
            throw new NotFoundException("Notification with id " + notificationId + " not found");
        }
        notificationRepository.deleteById(notificationId);
        return ResponseEntity.noContent().build();
    }
}
