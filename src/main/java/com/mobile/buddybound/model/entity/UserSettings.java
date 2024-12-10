package com.mobile.buddybound.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "user_settings")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "contact_enabled")
    private boolean contactEnabled;

    @Column(name = "location_enabled")
    private boolean locationEnabled;

    @Column(name = "location_history_enabled")
    private boolean locationHistoryEnabled;
}
