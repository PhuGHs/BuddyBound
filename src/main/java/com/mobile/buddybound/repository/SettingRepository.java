package com.mobile.buddybound.repository;

import com.mobile.buddybound.model.entity.UserSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingRepository extends JpaRepository<UserSettings, Long> {
}
