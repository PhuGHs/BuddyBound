package com.mobile.buddybound.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "user_images")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "image_id")
    private Image image;

    @Column(name = "is_main_avatar")
    private boolean mainAvatar;
}
