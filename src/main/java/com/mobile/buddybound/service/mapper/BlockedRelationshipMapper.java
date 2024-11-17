package com.mobile.buddybound.service.mapper;

import com.mobile.buddybound.model.dto.BlockedRelationshipDto;
import com.mobile.buddybound.model.entity.BlockedRelationship;
import com.mobile.buddybound.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class, UserService.class })
public interface BlockedRelationshipMapper extends EntityMapper<BlockedRelationship, BlockedRelationshipDto>{
    @Override
    @Mapping(source = "blockedUserId", target = "blockedUser")
    BlockedRelationship toEntity(BlockedRelationshipDto dto);
}
