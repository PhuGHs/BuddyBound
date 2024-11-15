package com.mobile.buddybound.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "birthdate")
    private LocalDateTime birthDate;

    @Column(name = "gender")
    private boolean gender;

    @OneToOne(mappedBy = "user")
    private Account account;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Member> members;

    @OneToMany(mappedBy = "user")
    private List<UserSafeZone> userSafeZones;

    @OneToMany(mappedBy = "user")
    private List<UserImage> images;

    @OneToMany(mappedBy = "sender")
    private List<Relationship> forwardRelationships;

    @OneToMany(mappedBy = "receiver")
    private List<Relationship> backwardRelationships;
}
