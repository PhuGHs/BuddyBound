package com.mobile.buddybound.service.mapper;

import com.mobile.buddybound.model.dto.LocationHistoryDto;
import com.mobile.buddybound.model.entity.LocationHistory;
import com.mobile.buddybound.service.UserService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public abstract class LocationHistoryMapper {
    @Autowired
    protected UserService userService;

    @Mapping(target = "user", expression = "java(userService.findById(locationHistoryDto.getUserId()))")
    public abstract LocationHistory toEntity(LocationHistoryDto locationHistoryDto);

    @Mapping(source = "user.id", target = "userId")
    public abstract LocationHistoryDto toDto(LocationHistory locationHistory);
}
