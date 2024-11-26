package com.mobile.buddybound.repository;

import com.mobile.buddybound.model.entity.FriendRelationship;
import com.mobile.buddybound.model.entity.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FriendRelationshipRepository extends JpaRepository<FriendRelationship, Long>, JpaSpecificationExecutor<FriendRelationship> {
}
