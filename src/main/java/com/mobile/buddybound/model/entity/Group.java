package com.mobile.buddybound.model.entity;

import com.mobile.buddybound.model.enumeration.GroupType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Table(name = "chat_group")
@Entity
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class Group extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "group_description")
    private String groupDescription;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(name = "group_type")
    @Enumerated(EnumType.STRING)
    private GroupType groupType;

    @OneToMany(mappedBy = "group")
    private List<Member> members;

    @OneToMany(mappedBy = "group")
    private List<Message> messages;
}
