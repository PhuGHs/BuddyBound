package com.mobile.buddybound.service.mapper;

import com.mobile.buddybound.model.dto.MessageDto;
import com.mobile.buddybound.model.entity.Message;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { MemberMapper.class, ImageMapper.class })
public interface MessageMapper extends EntityMapper<Message, MessageDto> {
}
