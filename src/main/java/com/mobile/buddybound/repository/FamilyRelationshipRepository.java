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
}
