package com.mobile.buddybound.service.mapper;

import com.mobile.buddybound.model.dto.MemorableDestinationDto;
import com.mobile.buddybound.model.entity.MemorableDestination;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = LocationHistoryMapper.class)
public interface MemorableDestinationMapper extends EntityMapper<MemorableDestination, MemorableDestinationDto> {
}
