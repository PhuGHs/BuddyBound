package com.mobile.buddybound.pattern.factory_method;

import com.mobile.buddybound.model.dto.NotificationData;
import com.mobile.buddybound.model.entity.GroupInvitationNotification;
import com.mobile.buddybound.model.entity.Notification;
import com.mobile.buddybound.model.enumeration.NotificationType;
import com.mobile.buddybound.repository.NotificationRepository;
import com.mobile.buddybound.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GroupInvitationNotificationFactory implements NotificationFactory {
    private final UserService userService;
    private final NotificationRepository notificationRepository;
    @Override
    public Notification createNotification(NotificationType type, NotificationData data) {
        var currentUser = userService.getCurrentLoggedInUser();
        var recipient = userService.findById(data.getRecipientId());
        GroupInvitationNotification notification =  GroupInvitationNotification.builder()
                .notificationType(type)
                .sender(currentUser)
                .recipient(recipient)
                .groupName(data.getGroupName())
                .isRead(false)
                .referenceId(data.getReferenceId())
                .build();
        return notificationRepository.save(notification);
    }
}
