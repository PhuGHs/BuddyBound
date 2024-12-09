package com.mobile.buddybound.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("RELATIONSHIP_REQUEST")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class RelationshipRequestNotification extends Notification {
    @Column(name = "requestor_name")
    private String requestorName;

    @Column(name = "is_accepted")
    private boolean isAccepted;

    @Override
    public String getMessage() {
        return String.format("New relationship request from %s", requestorName);
    }
}
