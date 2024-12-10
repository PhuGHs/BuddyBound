package com.mobile.buddybound.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "gender")
    private boolean gender;

    @OneToOne(mappedBy = "user")
    private Account account;

    @OneToOne(mappedBy = "user")
    private UserSettings settings;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Member> members;

    @OneToMany(mappedBy = "user")
    private List<UserSafeZone> userSafeZones = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "sender")
    private List<Relationship> forwardRelationships = new ArrayList<>();

    @OneToMany(mappedBy = "receiver")
    private List<Relationship> backwardRelationships = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<BlockedRelationship> blockedRelationships = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<MemorableDestination> memorableDestinations = new ArrayList<>();
}
