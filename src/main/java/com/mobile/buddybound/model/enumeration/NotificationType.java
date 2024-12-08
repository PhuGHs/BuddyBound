package com.mobile.buddybound.model.enumeration;

import lombok.Getter;

@Getter
public enum NotificationType {
    COMMENT("COMMENT"),
    RELATIONSHIP_REQUEST("RELATIONSHIP_REQUEST"),
    GROUP_POST("GROUP_POST"),
    GROUP_INVITATION("GROUP_INVITATION");

    private final String value;

    NotificationType(String value) {
        this.value = value;
    }
}
