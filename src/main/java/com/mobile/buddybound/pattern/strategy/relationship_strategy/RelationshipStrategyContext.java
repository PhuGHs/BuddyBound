package com.mobile.buddybound.pattern.strategy.relationship_strategy;

import com.mobile.buddybound.model.enumeration.RelationshipType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RelationshipStrategyContext {
    private final FamilyRelationshipStrategy familyRelationshipStrategy;
    private final FriendRelationshipStrategy friendRelationshipStrategy;

    public RelationshipStrategy getStrategy(RelationshipType type) {
        return switch (type) {
            case FAMILY -> familyRelationshipStrategy;
            case FRIEND -> friendRelationshipStrategy;
            default -> throw new IllegalArgumentException("Unknown relationship type: " + type);
        };
    }
}
