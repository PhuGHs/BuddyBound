package com.mobile.buddybound.service.mapper;

import com.mobile.buddybound.model.dto.NotificationDto;
import com.mobile.buddybound.model.entity.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface NotificationMapper {
    NotificationDto toCommentNotificationDto(Notification notification);
    NotificationDto toGroupPostNotificationDto(Notification notification);
    NotificationDto toGroupInvitationNotificationDto(Notification notification);
    NotificationDto toRelationshipRequest(Notification notificationDto);
}
