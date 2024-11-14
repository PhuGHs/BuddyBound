package com.mobile.buddybound.pattern.abstract_factory;

import com.mobile.buddybound.model.dto.RelationshipDto;
import com.mobile.buddybound.model.entity.Relationship;
import com.mobile.buddybound.service.mapper.RelationshipMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class RelationshipFactory {
    protected RelationshipMapper mapper;
    public abstract Relationship createRelationship(RelationshipDto dto);
}
