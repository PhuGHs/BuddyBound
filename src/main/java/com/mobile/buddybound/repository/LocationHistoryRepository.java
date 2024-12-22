package com.mobile.buddybound.repository;

import com.mobile.buddybound.model.entity.LocationHistory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LocationHistoryRepository extends JpaRepository<LocationHistory, Long> {
    List<LocationHistory> findByUser_IdAndCreatedAtBetween(Long userId, LocalDateTime startDate, LocalDateTime endDate, Sort sort);
    List<LocationHistory> findByUser_IdAndCreatedAtLessThanEqual(
            Long userId,
            LocalDateTime endDate
    );

    List<LocationHistory> findByUser_IdAndCreatedAtGreaterThanEqual(
            Long userId,
            LocalDateTime startDate,
            Sort sort
    );
    List<LocationHistory> findByUser_Id(Long userId, Sort sort);

    @Query(value = "SELECT * FROM location_histories WHERE user_id = :userId ORDER BY created_at DESC LIMIT 1", nativeQuery = true)
    Optional<LocationHistory> findLatestLocationHistoryByUserId(Long userId);
}
