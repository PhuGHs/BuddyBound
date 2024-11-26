package com.mobile.buddybound.service.mapper;

import com.mobile.buddybound.model.dto.GroupDto;
import com.mobile.buddybound.model.entity.Group;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupMapper extends EntityMapper<Group, GroupDto> {
}
