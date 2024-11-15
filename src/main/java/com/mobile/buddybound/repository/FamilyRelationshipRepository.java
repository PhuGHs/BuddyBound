package com.mobile.buddybound.repository;

import com.mobile.buddybound.model.entity.FamilyRelationship;
import com.mobile.buddybound.model.enumeration.FamilyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamilyRelationshipRepository extends JpaRepository<FamilyRelationship, Long> {
    @Query("SELECT fr FROM FamilyRelationship fr WHERE fr.isPending = :isPending AND (fr.sender.id = :currentUserId OR fr.receiver.id = :currentUserId)")
    List<FamilyRelationship> getAll(boolean isPending, Long currentUserId);
}
