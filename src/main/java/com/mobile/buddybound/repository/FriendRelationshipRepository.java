package com.mobile.buddybound.repository;

import com.mobile.buddybound.model.entity.FriendRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FriendRelationshipRepository extends JpaRepository<FriendRelationship, Long> {
    @Query("SELECT fr FROM FriendRelationship fr WHERE fr.isPending = :isPending AND (fr.sender.id = :currentUserId OR fr.receiver.id = :currentUserId)")
    List<FriendRelationship> getAll(boolean isPending, Long currentUserId);
}
