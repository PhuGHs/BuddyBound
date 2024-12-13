package com.mobile.buddybound.repository;


import com.mobile.buddybound.model.entity.FriendRelationship;
import com.mobile.buddybound.model.entity.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRelationshipRepository extends JpaRepository<FriendRelationship, Long>, JpaSpecificationExecutor<FriendRelationship> {
    @Query("SELECT f FROM FriendRelationship f " +
            "WHERE (f.sender.id = :currentUserId AND f.receiver.id = :userId) OR (f.sender.id = :userId AND f.receiver.id = :currentUserId)")
    FriendRelationship getFriendRelationship(Long currentUserId, Long userId);
}
