package com.mobile.buddybound.model.entity;

import com.mobile.buddybound.model.enumeration.NotificationType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@DiscriminatorValue("COMMENT")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CommentNotification extends Notification {
    @Column(name = "comment_content")
    private String commentContent;

    @Column(name = "post_title")
    private String postTitle;

    @Override
    public String getMessage() {
        return String.format("New comment on post %s: %s", postTitle, commentContent);
    }
}
