package com.mobile.buddybound.service;

import com.mobile.buddybound.model.dto.LocationDto;
import com.mobile.buddybound.model.dto.MessageDto;
import com.mobile.buddybound.model.dto.NotificationData;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WebsocketServiceImpl implements WebsocketService {
    private final SimpMessagingTemplate messagingTemplate;
    private final static String messageTopic = "/topic/group/messages";
    private final static String locationTopic = "/topic/group/location";
    private final static String notificationTopic = "/topic/notification/users";
    @Override
    public void sendMessage(MessageDto messageDto) {
        messagingTemplate.convertAndSend(messageTopic + String.format("/%s", messageDto.getGroupId()), messageDto);
    }

    @Override
    public void sendLocationUpdate(Long groupId, LocationDto locationDto) {
        messagingTemplate.convertAndSend(locationTopic + String.format("/%s", groupId), locationDto);
    }

    @Override
    public void sendNotificationUpdate(NotificationData notificationData) {
        messagingTemplate.convertAndSend(locationTopic + String.format("/%s", notificationData.getRecipientId()), notificationData);
    }
}
