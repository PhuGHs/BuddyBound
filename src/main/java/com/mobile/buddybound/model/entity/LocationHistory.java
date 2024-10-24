package com.mobile.buddybound.model.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "location_histories")
public class LocationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longtitude")
    private double longitude;

    @CreatedDate
    @Column(name = "createdAt")
    private LocalDateTime createdAt;
}
