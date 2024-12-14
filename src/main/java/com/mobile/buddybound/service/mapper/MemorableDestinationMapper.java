package com.mobile.buddybound.service.mapper;

import com.mobile.buddybound.model.dto.MemorableDestinationDto;
import com.mobile.buddybound.model.entity.MemorableDestination;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = LocationHistoryMapper.class)
public interface MemorableDestinationMapper extends EntityMapper<MemorableDestination, MemorableDestinationDto> {
    @Override
    MemorableDestination toEntity(MemorableDestinationDto memorableDestinationDto);
}
