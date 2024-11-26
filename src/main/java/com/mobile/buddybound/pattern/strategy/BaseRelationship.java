package com.mobile.buddybound.pattern.strategy;

import com.mobile.buddybound.model.entity.User;

public interface BaseRelationship {
    User getSender();
    User getReceiver();
}
