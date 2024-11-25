package com.mobile.buddybound.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "group_relationship_context")
public class GroupRelationshipContext {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "relationship_id")
    private Relationship relationship;

    @Column(name = "group_specific_role")
    private String groupSpecificRole;
}
