package com.mobile.buddybound.repository;

import com.mobile.buddybound.model.entity.BlockedRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockedRelationshipRepository extends JpaRepository<BlockedRelationship, Long> {
    @Query("SELECT COUNT(*) > 0 FROM BlockedRelationship br WHERE br.blockedUser.id = :blockedUserId and br.user.id = :userId")
    boolean existsByBlockedUserAndUser(Long blockedUserId, Long userId);
}
