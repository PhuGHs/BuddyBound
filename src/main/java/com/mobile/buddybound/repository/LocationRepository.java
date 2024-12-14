package com.mobile.buddybound.repository;

import com.mobile.buddybound.model.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findByUser_Id(Long userId);

    @Query("SELECT l FROM Location l JOIN Member m ON l.user.id = m.user.id WHERE m.group.id = :groupId")
    List<Location> getUserLocationsWithinGroup(Long groupId);
}
