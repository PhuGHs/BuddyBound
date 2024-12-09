package com.mobile.buddybound.pattern.factory_method;

import com.mobile.buddybound.model.dto.NotificationData;
import com.mobile.buddybound.model.entity.CommentNotification;
import com.mobile.buddybound.model.entity.Notification;
import com.mobile.buddybound.model.enumeration.NotificationType;
import com.mobile.buddybound.repository.NotificationRepository;
import com.mobile.buddybound.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentNotificationFactory implements NotificationFactory {
    private final UserService userService;
    private final NotificationRepository notificationRepository;
    @Override
    public Notification createNotification(NotificationType type, NotificationData data) {
        var currentUser = userService.getCurrentLoggedInUser();
        var recipient = userService.findById(data.getRecipientId());
        CommentNotification notification =  CommentNotification.builder()
                .sender(currentUser)
                .recipient(recipient)
                .commentContent(data.getCommentContent())
                .notificationType(type)
                .referenceId(data.getReferenceId())
                .isRead(false)
                .postTitle(data.getPostTitle())
                .build();

        return notificationRepository.save(notification);
    }
}
