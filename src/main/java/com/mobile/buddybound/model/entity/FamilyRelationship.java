package com.mobile.buddybound.model.entity;

import com.mobile.buddybound.model.enumeration.FamilyRole;
import com.mobile.buddybound.model.enumeration.FamilyType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FamilyRelationship extends Relationship {
    @Enumerated(EnumType.STRING)
    private FamilyType familyType;

    @Enumerated(EnumType.STRING)
    private FamilyRole senderRole;

    @Enumerated(EnumType.STRING)
    private FamilyRole receiverRole;
}
