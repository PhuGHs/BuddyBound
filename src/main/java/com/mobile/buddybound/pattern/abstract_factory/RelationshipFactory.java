package com.mobile.buddybound.pattern.abstract_factory;

import com.mobile.buddybound.model.dto.RelationshipDto;
import com.mobile.buddybound.model.entity.Relationship;
import com.mobile.buddybound.model.entity.User;
import com.mobile.buddybound.service.mapper.RelationshipMapper;
import lombok.RequiredArgsConstructor;

public abstract class RelationshipFactory {
    public abstract Relationship createRelationship(RelationshipDto dto);
}
