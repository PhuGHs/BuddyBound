package com.mobile.buddybound.pattern.factory_method;

import com.mobile.buddybound.exception.NotFoundException;
import com.mobile.buddybound.model.enumeration.NotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class NotificationFactoryProvider {
    private final Map<NotificationType, NotificationFactory> factories;

    public NotificationFactory getFactory(NotificationType type) {
        NotificationFactory factory = factories.get(type);
        if (Objects.isNull(factory)) {
            throw new NotFoundException("Unknown notification type");
        }
        return factory;
    }
}
