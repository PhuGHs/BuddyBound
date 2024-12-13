package com.mobile.buddybound.repository;

import com.mobile.buddybound.model.entity.FamilyRelationship;
import com.mobile.buddybound.model.entity.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamilyRelationshipRepository extends JpaRepository<FamilyRelationship, Long>, JpaSpecificationExecutor<FamilyRelationship> {
    @Query("SELECT f FROM FamilyRelationship f " +
            "WHERE (f.sender.id = :currentUserId AND f.receiver.id = :userId) OR (f.sender.id = :userId AND f.receiver.id = :currentUserId)")
    FamilyRelationship findRelationship(Long currentUserId, Long userId);
}
