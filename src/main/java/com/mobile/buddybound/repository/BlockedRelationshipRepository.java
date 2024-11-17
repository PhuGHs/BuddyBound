package com.mobile.buddybound.repository;

import com.mobile.buddybound.model.entity.BlockedRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockedRelationshipRepository extends JpaRepository<BlockedRelationship, Long> {
}
