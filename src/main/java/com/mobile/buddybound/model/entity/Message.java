package com.mobile.buddybound.model.entity;

import jakarta.persistence.*;

import java.util.List;

@Table(name = "messages")
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

    @OneToMany(mappedBy = "message")
    private List<Image> images;
}
