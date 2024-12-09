package com.mobile.buddybound.model.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class NotificationData {
    private Long recipientId;
    private Long senderId;
    private Long referenceId;
    private String commentContent;
    private String postTitle;
    private String requesterName;
    private String groupName;
}
