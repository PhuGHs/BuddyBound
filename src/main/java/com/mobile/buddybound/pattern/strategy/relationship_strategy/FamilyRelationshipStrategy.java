package com.mobile.buddybound.pattern.strategy.relationship_strategy;

import com.mobile.buddybound.model.dto.RelationshipDto;
import com.mobile.buddybound.model.entity.FamilyRelationship;
import com.mobile.buddybound.repository.FamilyRelationshipRepository;
import com.mobile.buddybound.service.mapper.RelationshipMapper;
import com.mobile.buddybound.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FamilyRelationshipStrategy extends RelationshipStrategy {
    private final FamilyRelationshipRepository familyRelationshipRepository;
    private final RelationshipMapper relationshipMapper;
    private final UserMapper userMapper;

    @Override
    public List<RelationshipDto> getRelationships(Long currentUserId, String searchTerm, boolean isPending) {
        Specification<FamilyRelationship> familySpec = this.getSpecification(currentUserId, searchTerm, isPending, FamilyRelationship.class);
        List<FamilyRelationship> relationships = familyRelationshipRepository.findAll(familySpec);
        return relationships.stream().map(r -> {
            RelationshipDto dto = relationshipMapper.toFamilyRelationshipDto(r);
            dto.setReceiver(
                    userMapper.toDto(
                            r.getReceiver().getId().equals(currentUserId)
                            ? r.getSender() : r.getReceiver()
                    )
            );
            return dto;
        }).toList();
    }
}
