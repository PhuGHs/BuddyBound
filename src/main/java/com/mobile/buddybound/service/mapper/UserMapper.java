package com.mobile.buddybound.service.mapper;

import com.mobile.buddybound.model.dto.UserDto;
import com.mobile.buddybound.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<User, UserDto> {
    @Mapping(
            target = "avatar",
            expression = "java(user.getImages() != null ? user.getImages().stream().filter(img -> img.isMainAvatar()).findFirst().map(img -> img.getImage().getImageUrl()).orElse(null) : null)"
    )
    UserDto toDto (User user);
}
