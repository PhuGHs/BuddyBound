package com.mobile.buddybound.pattern.abstract_factory;

import com.mobile.buddybound.model.dto.RelationshipDto;
import com.mobile.buddybound.model.entity.Relationship;
import org.springframework.stereotype.Service;

@Service
public class FamilyRelationshipFactory extends RelationshipFactory {
    @Override
    public Relationship createRelationship(RelationshipDto dto) {
        return mapper.toFamilyRelationship(dto);
    }
}
