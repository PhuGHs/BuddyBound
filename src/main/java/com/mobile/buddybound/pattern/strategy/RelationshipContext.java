package com.mobile.buddybound.pattern.strategy;

import com.mobile.buddybound.model.enumeration.RelationshipType;
import com.mobile.buddybound.service.mapper.RelationshipMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

@RequiredArgsConstructor
public abstract class RelationshipContext {
    protected final JpaSpecificationExecutor<?> repository;
    protected final RelationshipMapper relationshipMapper;

    public abstract List<? extends BaseRelationship> findAllRelationships(Long currentUserId, boolean isPending, String searchTerm);
}
