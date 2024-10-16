package com.mobile.buddybound.model.entity;

import jakarta.persistence.*;

@Table(name = "user_images")
@Entity
public class UserImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private Image image;

    @Column(name = "is_main_avatar")
    private boolean mainAvatar;
}
