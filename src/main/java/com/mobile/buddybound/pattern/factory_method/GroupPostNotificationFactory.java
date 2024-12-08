package com.mobile.buddybound.pattern.factory_method;

import com.mobile.buddybound.model.entity.GroupPostNotification;
import com.mobile.buddybound.model.entity.Notification;
import org.springframework.stereotype.Component;

@Component
public class GroupPostNotificationFactory implements NotificationFactory {
    @Override
    public Notification createNotification() {
        return GroupPostNotification.builder().build();
    }
}
