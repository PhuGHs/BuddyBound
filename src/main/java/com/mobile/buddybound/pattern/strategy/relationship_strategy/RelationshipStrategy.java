package com.mobile.buddybound.pattern.strategy.relationship_strategy;

import com.mobile.buddybound.model.dto.RelationshipDto;
import com.mobile.buddybound.repository.specification.RelationshipSpecification;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public abstract class RelationshipStrategy {
    public abstract List<RelationshipDto> getRelationships(Long currentUserId, String searchTerm, boolean isPending);
    protected <T> Specification<T> getSpecification(Long currentUserId, String searchTerm, boolean isPending, Class<T> type) {
        return Specification.where(RelationshipSpecification.hasCurrentUserId(currentUserId, type))
                .and(RelationshipSpecification.hasPendingStatus(isPending, type))
                .and(RelationshipSpecification.hasUserAndFullNameMatch(currentUserId, searchTerm, type));
    }
}
