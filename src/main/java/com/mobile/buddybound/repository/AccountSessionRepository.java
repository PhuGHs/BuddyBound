package com.mobile.buddybound.repository;

import com.mobile.buddybound.model.entity.AccountSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountSessionRepository extends JpaRepository<AccountSession, Long> {
    Optional<AccountSession> findByRefreshToken(String refreshToken);
}
