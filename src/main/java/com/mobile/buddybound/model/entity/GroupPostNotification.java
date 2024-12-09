package com.mobile.buddybound.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("GROUP_POST")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class GroupPostNotification extends Notification {
    @Column(name = "group_name")
    private String groupName;

    @Column(name = "post_title")
    private String postTitle;

    @Column(name = "group_avatar")
    private String groupAvatar;

    @Override
    public String getMessage() {
        return String.format("New post in %s: %s", groupName, postTitle);
    }
}
