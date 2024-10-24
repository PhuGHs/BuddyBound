package com.mobile.buddybound.service.mapper;

import com.mobile.buddybound.model.dto.UserDto;
import com.mobile.buddybound.model.entity.User;

public interface UserMapper extends EntityMapper<User, UserDto> {
    User toEntity (UserDto dto);
    UserDto toDto (User user);
}
