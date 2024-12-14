package com.mobile.buddybound.repository;

import com.mobile.buddybound.model.entity.User;
import com.mobile.buddybound.model.entity.UserSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SettingRepository extends JpaRepository<UserSettings, Long> {
    Optional<UserSettings> findByUser_Id(Long userId);
}
