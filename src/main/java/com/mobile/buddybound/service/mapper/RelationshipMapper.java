package com.mobile.buddybound.service.mapper;

import com.mobile.buddybound.model.dto.RelationshipDto;
import com.mobile.buddybound.model.entity.FamilyRelationship;
import com.mobile.buddybound.model.entity.FriendRelationship;
import com.mobile.buddybound.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class, UserService.class })
public interface RelationshipMapper {
    @Mapping(source = "receiverId", target = "receiver")
    FamilyRelationship toFamilyRelationship(RelationshipDto dto);

    @Mapping(source = "receiver", target = "receiver")
    RelationshipDto toFamilyRelationshipDto(FamilyRelationship relationship);

    @Mapping(source = "receiver", target = "receiver")
    RelationshipDto toFriendRelationshipDto(FriendRelationship relationship);

    // Mapping for FriendRelationship
    @Mapping(source = "receiverId", target = "receiver")
    FriendRelationship toFriendRelationship(RelationshipDto dto);
}
