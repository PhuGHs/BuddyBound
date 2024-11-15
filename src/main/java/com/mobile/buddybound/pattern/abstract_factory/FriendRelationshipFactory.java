package com.mobile.buddybound.pattern.abstract_factory;

import com.mobile.buddybound.model.dto.RelationshipDto;
import com.mobile.buddybound.model.entity.Relationship;
import com.mobile.buddybound.service.mapper.RelationshipMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendRelationshipFactory extends RelationshipFactory {
    private final RelationshipMapper mapper;
    @Override
    public Relationship createRelationship(RelationshipDto dto) {
        return mapper.toFriendRelationship(dto);
    }
}