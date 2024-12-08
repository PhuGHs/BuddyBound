package com.mobile.buddybound.pattern.factory_method;

import com.mobile.buddybound.model.entity.CommentNotification;
import com.mobile.buddybound.model.entity.Notification;
import org.springframework.stereotype.Component;

@Component
public class CommentNotificationFactory implements NotificationFactory {
    @Override
    public Notification createNotification() {
        return CommentNotification.builder().build();
    }
}
