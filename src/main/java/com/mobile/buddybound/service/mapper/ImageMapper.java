package com.mobile.buddybound.service.mapper;

import com.mobile.buddybound.model.dto.ImageDto;
import com.mobile.buddybound.model.entity.Image;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ImageMapper extends EntityMapper<Image, ImageDto> {
}
