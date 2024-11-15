package com.mobile.buddybound.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class RelationshipDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long senderId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long receiverId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UserDto sender;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UserDto receiver;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private RelationshipType relationshipType;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String content;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean isPending;

    private FamilyType familyType;

    private FriendType friendType;
}
