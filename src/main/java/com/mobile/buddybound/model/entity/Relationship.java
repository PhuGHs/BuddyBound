package com.mobile.buddybound.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "relationships")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public abstract class Relationship extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    protected User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    protected User receiver;

    @Column(name = "is_pending")
    protected boolean isPending = true;
}
