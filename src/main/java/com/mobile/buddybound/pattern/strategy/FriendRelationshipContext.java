package com.mobile.buddybound.pattern.strategy;

import com.mobile.buddybound.model.entity.FriendRelationship;
import com.mobile.buddybound.repository.specification.RelationshipSpecification;
import com.mobile.buddybound.service.mapper.RelationshipMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public class FriendRelationshipContext extends RelationshipContext {
    public FriendRelationshipContext(JpaSpecificationExecutor<?> repository, RelationshipMapper relationshipMapper) {
        super(repository, relationshipMapper);
    }

    @Override
    public List<? extends BaseRelationship> findAllRelationships(Long currentUserId, boolean isPending, String searchTerm) {
        Specification<FriendRelationship> spec = Specification.where(
                        RelationshipSpecification.hasCurrentUserId(currentUserId, FriendRelationship.class))
                .and(RelationshipSpecification.hasPendingStatus(isPending, FriendRelationship.class))
                .and(RelationshipSpecification.hasUserAndFullNameMatch(currentUserId, searchTerm, FriendRelationship.class)
                );
        return repository.findAll(spec);
    }
}
