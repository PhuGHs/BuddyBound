package com.mobile.buddybound.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "relationship_types")
public class RelationshipType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category")
    private String category;

    @Column(name = "sub_category")
    private String subCategory;

    @Column(name = "description")
    private String description;
}
