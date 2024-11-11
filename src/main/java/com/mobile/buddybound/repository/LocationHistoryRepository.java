package com.mobile.buddybound.repository;

import com.mobile.buddybound.model.entity.LocationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationHistoryRepository extends JpaRepository<LocationHistory, Long> {
    List<LocationHistory> findByUserId(Long userId);
}
