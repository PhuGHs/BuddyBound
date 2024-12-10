package com.mobile.buddybound.service.mapper;

import com.mobile.buddybound.model.dto.SettingDto;
import com.mobile.buddybound.model.entity.UserSettings;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SettingMapper extends EntityMapper<UserSettings, SettingDto>{
    @Override
    @Mapping(source = "user.id", target = "userId")
    SettingDto toDto(UserSettings userSettings);
}
