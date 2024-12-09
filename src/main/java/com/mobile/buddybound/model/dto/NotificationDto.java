package com.mobile.buddybound.model.dto;

import com.fasterxml.jackson.annotation.*;
import com.mobile.buddybound.model.enumeration.NotificationType;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationDto {
    @JsonView(Views.Read.class)
    private Long id;

    @JsonView(Views.Read.class)
    private UserDto sender;

    @JsonView(Views.Read.class)
    private UserDto recipient;

    @JsonView(Views.Read.class)
    @JsonProperty("isRead")
    private boolean isRead;

    @JsonView(Views.Read.class)
    @JsonProperty("isAccepted")
    private boolean isAccepted;

    @JsonView(Views.Read.class)
    private NotificationType notificationType;

    @JsonView(Views.Read.class)
    private Long referenceId;

    @JsonView(Views.Read.class)
    private LocalDateTime createdAt;

    @JsonView(Views.Read.class)
    private LocalDateTime updatedAt;

    @JsonView(Views.Read.class)
    private String postTitle;

    @JsonView(Views.Read.class)
    private String message;

    @JsonView(Views.Read.class)
    private String commentContent;

    @JsonView(Views.Read.class)
    private String groupAvatar;

    @JsonView(Views.Read.class)
    private String groupName;

    @JsonView(Views.Read.class)
    private String requestorName;
}
