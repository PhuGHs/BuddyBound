package com.mobile.buddybound.repository;

import com.mobile.buddybound.model.entity.MemorableDestination;
import com.mobile.buddybound.model.enumeration.MemorableDestinationType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemorableDestinationRepository extends JpaRepository<MemorableDestination, Long> {
    @Query(value = """
        SELECT md FROM MemorableDestination md
        JOIN md.location lh
        WHERE
            6371 * ACOS(
                COS(RADIANS(:latitude)) * COS(RADIANS(lh.latitude)) *
                COS(RADIANS(lh.longitude) - RADIANS(:longitude)) +
                SIN(RADIANS(:latitude)) * SIN(RADIANS(lh.latitude))
            ) <= 5
        AND md.locationType = :locationType
        AND md.user.id = :userId
        """)
    List<MemorableDestination> findNearbyDestinationsByType(
            @Param("userId") Long userId,
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
            @Param("locationType") MemorableDestinationType locationType);

    List<MemorableDestination> getAllByUser_IdAndLocationType(Long userId, MemorableDestinationType type);
    List<MemorableDestination> getAllByUser_Id(Long userId);
}
