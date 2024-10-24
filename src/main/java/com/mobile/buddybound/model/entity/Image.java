package com.mobile.buddybound.model.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "message_id")
    private Message message;

    @OneToMany(mappedBy = "image")
    private List<UserImage> images;
}
