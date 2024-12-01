package com.mobile.buddybound.repository;

import com.mobile.buddybound.model.entity.MemorableDestination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemorableDestinationRepository extends JpaRepository<MemorableDestination, Long> {
}
