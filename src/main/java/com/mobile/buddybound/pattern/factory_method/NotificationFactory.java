package com.mobile.buddybound.pattern.factory_method;

import com.mobile.buddybound.model.dto.NotificationData;
import com.mobile.buddybound.model.entity.Notification;
import com.mobile.buddybound.model.enumeration.NotificationType;

public interface NotificationFactory {
    Notification createNotification(NotificationType type, NotificationData data);
}
