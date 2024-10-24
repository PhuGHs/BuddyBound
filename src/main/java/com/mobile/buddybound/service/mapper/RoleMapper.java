package com.mobile.buddybound.service.mapper;

import com.mobile.buddybound.model.dto.RoleDto;
import com.mobile.buddybound.model.entity.Role;

public interface RoleMapper extends EntityMapper<Role, RoleDto> {
    Role toEntity(RoleDto roleDto);
    RoleDto toDto(Role role);
}
