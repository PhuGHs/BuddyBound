package com.mobile.buddybound.pattern.singleton;

import com.google.common.base.Objects;
import com.mobile.buddybound.exception.NotFoundException;
import com.mobile.buddybound.model.dto.SettingDto;
import com.mobile.buddybound.model.entity.UserSettings;
import com.mobile.buddybound.repository.SettingRepository;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Scope("singleton")
public class UserSettingManager {
    private static UserSettingManager instance;
    private final SettingRepository settingRepository;
    private final Map<Long, UserSettings> userSettingsCache;

    private UserSettingManager(SettingRepository settingRepository) {
        this.settingRepository = settingRepository;
        this.userSettingsCache = new ConcurrentHashMap<>();
    }

    public static synchronized UserSettingManager getInstance(SettingRepository settingRepository) {
        if (Objects.equal(instance, null)) {
            instance = new UserSettingManager(settingRepository);
        }
        return instance;
    }

    public synchronized UserSettings updateSettings(SettingDto dto) {
        UserSettings settings = userSettingsCache.computeIfAbsent(dto.getUserId(),
                id -> settingRepository.findByUser_Id(id)
                        .orElseThrow(() -> new NotFoundException("Settings not found for user: " + id)));

        settings.setContactEnabled(dto.isContactEnabled());
        settings.setLocationEnabled(dto.isLocationEnabled());
        settings.setLocationHistoryEnabled(dto.isLocationHistoryEnabled());

        UserSettings savedSettings = settingRepository.save(settings);
        userSettingsCache.put(dto.getUserId(), savedSettings);

        return savedSettings;
    }

    public synchronized UserSettings getSettings(Long userId) {
        return userSettingsCache.computeIfAbsent(userId,
                id -> settingRepository.findByUser_Id(id)
                        .orElseThrow(() -> new NotFoundException("Settings not found for user: " + id)));
    }

    public synchronized void clearCache(Long userId) {
        userSettingsCache.remove(userId);
    }
}
