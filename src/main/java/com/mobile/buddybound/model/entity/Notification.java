package com.mobile.buddybound.model.entity;

import com.mobile.buddybound.model.enumeration.NotificationType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "notifications")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "notification_type", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class Notification extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false)
    protected User recipient;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    protected User sender;

    protected Long referenceId;
    protected boolean isRead;

    @Column(insertable = false, updatable = false, name = "notification_type")
    @Enumerated(EnumType.STRING)
    protected NotificationType notificationType;

    public abstract String getMessage();
}
