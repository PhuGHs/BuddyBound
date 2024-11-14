package com.mobile.buddybound.model.entity;

import com.mobile.buddybound.model.enumeration.FriendType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FriendRelationship extends Relationship {
    @Enumerated(EnumType.STRING)
    private FriendType friendType;
}
