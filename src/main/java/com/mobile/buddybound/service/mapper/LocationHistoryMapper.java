package com.mobile.buddybound.service.mapper;

import com.mobile.buddybound.model.dto.LocationHistoryDto;
import com.mobile.buddybound.model.entity.LocationHistory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface LocationHistoryMapper extends EntityMapper<LocationHistory, LocationHistoryDto>{
}
