package com.mobile.buddybound.pattern.strategy.relationship_strategy;

import com.mobile.buddybound.model.dto.RelationshipDto;
import com.mobile.buddybound.model.entity.FriendRelationship;
import com.mobile.buddybound.repository.FriendRelationshipRepository;
import com.mobile.buddybound.service.mapper.RelationshipMapper;
import com.mobile.buddybound.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FriendRelationshipStrategy extends RelationshipStrategy {
    private final FriendRelationshipRepository friendRelationshipRepository;
    private final RelationshipMapper relationshipMapper;
    private final UserMapper userMapper;

    @Override
    public List<RelationshipDto> getRelationships(Long currentUserId, String searchTerm, boolean isPending) {
        Specification<FriendRelationship> friendSpec = this.getSpecification(currentUserId, searchTerm, isPending, FriendRelationship.class);

        List<FriendRelationship> relationships = friendRelationshipRepository.findAll(friendSpec);
        return relationships.stream().map(r -> {
            RelationshipDto dto = relationshipMapper.toFriendRelationshipDto(r);
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
