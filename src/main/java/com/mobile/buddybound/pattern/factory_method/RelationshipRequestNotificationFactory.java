package com.mobile.buddybound.pattern.factory_method;

import com.mobile.buddybound.model.dto.NotificationData;
import com.mobile.buddybound.model.entity.Notification;
import com.mobile.buddybound.model.entity.RelationshipRequestNotification;
import com.mobile.buddybound.model.enumeration.NotificationType;
import com.mobile.buddybound.repository.NotificationRepository;
import com.mobile.buddybound.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RelationshipRequestNotificationFactory implements NotificationFactory {
    private final UserService userService;
    private final NotificationRepository notificationRepository;
    @Override
    public Notification createNotification(NotificationType type, NotificationData data) {
        var recipient = userService.findById(data.getRecipientId());
        var sender = userService.getCurrentLoggedInUser();
        RelationshipRequestNotification notification = RelationshipRequestNotification.builder()
                .sender(sender)
                .recipient(recipient)
                .isRead(false)
                .referenceId(data.getReferenceId())
                .notificationType(type)
                .requestorName(data.getRequesterName())
                .isAccepted(false)
                .build();
        return notificationRepository.save(notification);
    }
}
