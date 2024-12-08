package com.mobile.buddybound.pattern.factory_method;

import com.mobile.buddybound.model.entity.GroupInvitationNotification;
import com.mobile.buddybound.model.enumeration.NotificationType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class NotificationFactoryConfig {

    @Bean
    public Map<NotificationType, NotificationFactory> notificationFactories(
            CommentNotificationFactory commentFactory,
            GroupInvitationNotificationFactory groupInvitationFactory,
            GroupPostNotificationFactory groupPostFactory,
            RelationshipRequestNotificationFactory relationshipRequestFactory
    ) {
        Map<NotificationType, NotificationFactory> factories = new HashMap<>();
        factories.put(NotificationType.COMMENT, commentFactory);
        factories.put(NotificationType.RELATIONSHIP_REQUEST, relationshipRequestFactory);
        factories.put(NotificationType.GROUP_INVITATION, groupInvitationFactory);
        factories.put(NotificationType.GROUP_POST, groupPostFactory);
        return factories;
    }
}
