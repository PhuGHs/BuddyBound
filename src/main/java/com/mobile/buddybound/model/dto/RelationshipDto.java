package com.mobile.buddybound.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.mobile.buddybound.model.enumeration.FamilyType;
import com.mobile.buddybound.model.enumeration.FriendType;
import com.mobile.buddybound.model.enumeration.RelationshipType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RelationshipDto {
    public interface CreateView {}
    public interface ReadView {}

    @JsonView(ReadView.class)
    private Long id;

    private Long senderId;
    private Long receiverId;
    private RelationshipType relationshipType;
    @JsonView(ReadView.class)
    private String content;
    @JsonView(ReadView.class)
    private boolean isPending;
    private FamilyType familyType;
    private FriendType friendType;
}
