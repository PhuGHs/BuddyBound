package com.mobile.buddybound.service;

import com.mobile.buddybound.model.dto.LocationDto;
import com.mobile.buddybound.model.dto.LocationUpdateDto;
import com.mobile.buddybound.model.dto.MessageDto;
import com.mobile.buddybound.model.dto.NotificationData;
import org.springframework.stereotype.Service;

@Service
public interface WebsocketService {
    void sendMessage(MessageDto messageDto);
    void sendLocationUpdate(Long groupId, LocationUpdateDto locationDto);
    void sendNotificationUpdate(NotificationData notificationData);
}
