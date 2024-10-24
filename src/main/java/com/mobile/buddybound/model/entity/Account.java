package com.mobile.buddybound.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "accounts")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Account extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "verification_code")
    private String verificationCode;

    @OneToMany(mappedBy = "account")
    private List<AccountSession> sessions;

    public Account(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
