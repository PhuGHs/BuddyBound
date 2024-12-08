package com.mobile.buddybound.pattern.factory_method;

import com.mobile.buddybound.model.entity.GroupInvitationNotification;
import com.mobile.buddybound.model.entity.Notification;
import org.springframework.stereotype.Component;

@Component
public class GroupInvitationNotificationFactory implements NotificationFactory {
    @Override
    public Notification createNotification() {
        return GroupInvitationNotification.builder().build();
    }
}
