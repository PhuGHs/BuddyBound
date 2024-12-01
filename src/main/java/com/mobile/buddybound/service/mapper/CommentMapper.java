package com.mobile.buddybound.service.mapper;

import com.mobile.buddybound.model.dto.CommentDto;
import com.mobile.buddybound.model.entity.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { MemberMapper.class })
public interface CommentMapper extends EntityMapper<Comment, CommentDto> {
}
