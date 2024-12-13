package com.mobile.buddybound.service.mapper;

import com.mobile.buddybound.model.dto.LocationHistoryDto;
import com.mobile.buddybound.model.entity.LocationHistory;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public abstract class LocationHistoryMapper {
    public abstract LocationHistory toEntity(LocationHistoryDto locationHistoryDto);

    @Mapping(source = "user", target = "user")
    public abstract LocationHistoryDto toDto(LocationHistory locationHistory);
}
