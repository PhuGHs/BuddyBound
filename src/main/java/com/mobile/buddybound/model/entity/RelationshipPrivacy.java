package com.mobile.buddybound.model.entity;

import jakarta.persistence.*;

@Table(name = "relationship_privacies")
@Entity
public class RelationshipPrivacy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "relationship_id")
    private Relationship relationship;

    @Column(name = "share_locations")
    private boolean shareLocations;

    @Column(name = "share_safe_zones")
    private boolean shareSafeZones;

    @Column(name = "share_contacts")
    private boolean shareContacts;
}
