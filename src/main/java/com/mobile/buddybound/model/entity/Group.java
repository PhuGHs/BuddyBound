package com.mobile.buddybound.model.entity;

import jakarta.persistence.*;

import java.util.List;

@Table(name = "chat_group")
@Entity
public class Group extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "group_name")
    private String groupName;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(name = "group_type")
    private String groupType;

    @OneToMany(mappedBy = "group")
    private List<Member> members;

    @OneToMany(mappedBy = "group")
    private List<Message> messages;
}
