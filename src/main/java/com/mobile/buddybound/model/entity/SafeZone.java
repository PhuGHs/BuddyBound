package com.mobile.buddybound.model.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "safe_zones")
public class SafeZone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "latitude", nullable = false)
    private double latitude;

    @Column(name = "longitude", nullable = false)
    private double longitude;

    @Column(name = "radius")
    private float radius;

    @OneToMany(mappedBy = "safeZone")
    private List<UserSafeZone> userSafeZones;
}
