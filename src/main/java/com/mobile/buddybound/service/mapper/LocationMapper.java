package com.mobile.buddybound.service.mapper;

import com.mobile.buddybound.model.dto.LocationDto;
import com.mobile.buddybound.model.entity.Location;
import lombok.AllArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface LocationMapper extends EntityMapper<Location, LocationDto> {
    @Override
    @Mapping(source = "user", target = "user")
    LocationDto toDto(Location location);

    @Override
    Location toEntity(LocationDto dto);
}
