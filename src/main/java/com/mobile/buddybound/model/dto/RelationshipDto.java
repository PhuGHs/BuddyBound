package com.mobile.buddybound.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.mobile.buddybound.model.enumeration.FamilyRole;
import com.mobile.buddybound.model.enumeration.FamilyType;
import com.mobile.buddybound.model.enumeration.FriendType;
import com.mobile.buddybound.model.enumeration.RelationshipType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class RelationshipDto {
    @JsonView({Views.Read.class, Views.Update.class})
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonView(Views.Create.class)
    private Long receiverId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonView({Views.Read.class})
    private UserDto receiver;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonView({Views.Create.class, Views.Update.class})
    private RelationshipType relationshipType;

    @JsonView({Views.Create.class, Views.Read.class, Views.Update.class})
    private FamilyType familyType;

    @JsonView({Views.Create.class, Views.Read.class, Views.Update.class})
    private FriendType friendType;

    @JsonView({Views.Create.class, Views.Read.class, Views.Update.class})
    private FamilyRole senderRole;

    @JsonView({Views.Create.class, Views.Read.class, Views.Update.class})
    private FamilyRole receiverRole;

    @JsonView({Views.Read.class})
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt = LocalDateTime.now();

    @JsonView({Views.Read.class})
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt = LocalDateTime.now();

}
