package com.mobile.buddybound.service.mapper;

import com.mobile.buddybound.model.dto.UserImageDto;
import com.mobile.buddybound.model.entity.UserImage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ImageMapper.class})
public interface UserImageMapper extends EntityMapper<UserImage, UserImageDto> {
}
