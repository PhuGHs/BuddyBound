package com.mobile.buddybound.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "account_session")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "refresh_token", nullable = false, length = 512)
    private String refreshToken;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "last_used", nullable = false)
    private LocalDateTime lastUsedAt = LocalDateTime.now();

    @Column(name = "is_revoked", nullable = false)
    private Boolean isRevoked = false;
}
