package com.mobile.buddybound.service.mapper;

import com.mobile.buddybound.model.dto.NotificationDto;
import com.mobile.buddybound.model.entity.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface NotificationMapper {
    NotificationDto toCommentNotificationDto(CommentNotification notification);
    NotificationDto toGroupPostNotificationDto(GroupPostNotification notification);
    NotificationDto toGroupInvitationNotificationDto(GroupInvitationNotification notification);
    NotificationDto toRelationshipRequest(RelationshipRequestNotification notificationDto);
}
