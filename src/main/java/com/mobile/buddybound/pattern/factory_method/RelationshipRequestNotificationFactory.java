package com.mobile.buddybound.pattern.factory_method;

import com.mobile.buddybound.model.entity.Notification;
import com.mobile.buddybound.model.entity.RelationshipRequestNotification;
import org.springframework.stereotype.Component;

@Component
public class RelationshipRequestNotificationFactory implements NotificationFactory {
    @Override
    public Notification createNotification() {
        return RelationshipRequestNotification.builder().build();
    }
}
