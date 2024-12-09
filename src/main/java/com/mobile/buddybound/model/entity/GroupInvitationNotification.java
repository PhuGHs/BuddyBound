package com.mobile.buddybound.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("GROUP_INVITATION")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class GroupInvitationNotification extends Notification {
    @Column(name = "group_name")
    private String groupName;

    @Column(name = "group_avatar")
    private String groupAvatar;

    @Override
    public String getMessage() {
        return String.format("You are invited to %s", groupName);
    }
}