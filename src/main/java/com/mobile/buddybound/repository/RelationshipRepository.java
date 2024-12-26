package com.mobile.buddybound.repository;

import com.mobile.buddybound.model.entity.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationshipRepository extends JpaRepository<Relationship, Long> {
    @Query("SELECT COUNT(r) > 0 from Relationship r WHERE (r.sender.id = :currentUserId and r.receiver.id = :receiverId) or (r.sender.id = :receiverId and r.receiver.id = :currentUserId)")
    boolean checkIfExist(Long currentUserId, Long receiverId);

    boolean existsBySenderIdOrReceiverId(Long senderId, Long receiverId);

    @Query("SELECT COUNT(r) > 0 from Relationship r WHERE ((r.sender.id = :currentUserId and r.receiver.id = :receiverId) or (r.sender.id = :receiverId and r.receiver.id = :currentUserId)) AND r.isPending = true")
    boolean checkIfRelationshipPending(Long currentUserId, Long receiverId);
}
