package com.mobile.buddybound.service.mapper;

import com.mobile.buddybound.model.dto.MessageDto;
import com.mobile.buddybound.model.dto.MessagePostDto;
import com.mobile.buddybound.model.entity.Message;
import com.mobile.buddybound.service.GroupService;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { MemberMapper.class, ImageMapper.class })
public interface MessageMapper extends EntityMapper<Message, MessageDto> {
    @Mapping(target = "group", expression = "java(groupService.findGroupById(dto.getGroupId()))")
    @Mapping(target = "member", expression = "java(groupService.findMemberByUserId(dto.getSenderId(), dto.getGroupId()))")
    Message toEntity(MessageDto dto, @Context GroupService groupService);

    @Mapping(target = "group", expression = "java(groupService.findGroupById(dto.getGroupId()))")
    Message toEntityOther(MessagePostDto dto, @Context GroupService groupService);

    @Override
    @Mapping(source = "group.id", target = "groupId")
    MessageDto toDto(Message message);
}