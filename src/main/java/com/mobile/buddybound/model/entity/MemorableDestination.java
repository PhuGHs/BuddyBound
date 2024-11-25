package com.mobile.buddybound.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "memorable_destinations")
public class MemorableDestination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private LocationHistory location;
}
