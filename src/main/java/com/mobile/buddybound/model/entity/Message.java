package com.mobile.buddybound.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Table(name = "messages")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Message extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @Column(name = "content")
    private String content;

    @ManyToMany
    @JoinTable(
            name = "message_images",
            joinColumns = @JoinColumn(name = "message_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    private List<Image> images;
}
