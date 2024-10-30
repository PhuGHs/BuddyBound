package com.mobile.buddybound.repository;

import com.mobile.buddybound.model.entity.AccountSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountSessionRepository extends JpaRepository<AccountSession, Long> {
    Optional<AccountSession> findByRefreshToken(String refreshToken);

    @Query("SELECT a FROM AccountSession as a WHERE a.account.id = :accountId")
    List<AccountSession> findByAccountId(Long accountId);

    @Modifying
    @Query("UPDATE AccountSession a set a.isRevoked = true WHERE  a.account.id = :accountId")
    void updateSessionByAccountId(Long accountId);
}
