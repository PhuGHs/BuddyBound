package com.mobile.buddybound.service.mapper;

import com.mobile.buddybound.model.dto.AlbumDto;
import com.mobile.buddybound.model.entity.Album;
import com.mobile.buddybound.service.PostService;
import com.mobile.buddybound.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {UserMapper.class, PostMapper.class})
public abstract class AlbumMapper {
    @Autowired
    protected PostService postService;
    @Autowired
    protected UserService userService;

    @Mapping(source = "createdAt", target = "createdAt")
    public abstract AlbumDto toDto(Album album);

    @Mapping(target = "posts", expression = "java(albumDto.getPostIdList().stream().map(id -> postService.getPost(id)).toList())")
    @Mapping(source = "id", target = "id")
    public abstract Album toEntity(AlbumDto albumDto);
}
