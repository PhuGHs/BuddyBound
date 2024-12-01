package com.mobile.buddybound.service.mapper;

import com.mobile.buddybound.model.dto.PostCreateDto;
import com.mobile.buddybound.model.dto.PostDto;
import com.mobile.buddybound.model.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { LocationHistoryMapper.class, MemberMapper.class, ImageMapper.class, CommentMapper.class })
public interface PostMapper extends EntityMapper<Post, PostDto> {
    @Override
    @Mapping(target = "commentCount", expression = "java(post.getComments() != null ? post.getComments().size() : 0)")
    PostDto toDto(Post post);

    Post toEntity(PostCreateDto postCreateDto);
}
