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

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RelationshipDto {
    @JsonView({Views.Read.class, Views.Update.class})
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonView(Views.Create.class)
    private Long receiverId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonView({Views.Read.class})
    private UserDto sender;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonView({Views.Read.class})
    private UserDto receiver;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonView({Views.Create.class, Views.Update.class})
    private RelationshipType relationshipType;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonView({Views.Read.class})
    private String content;

    @JsonView({Views.Read.class})
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean pending = true;

    @JsonView({Views.Create.class, Views.Read.class, Views.Update.class})
    private FamilyType familyType;

    @JsonView({Views.Create.class, Views.Read.class, Views.Update.class})
    private FriendType friendType;

    @JsonView({Views.Read.class})
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt = LocalDateTime.now();

    @JsonView({Views.Read.class})
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt = LocalDateTime.now();

}
