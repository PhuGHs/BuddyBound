package com.mobile.buddybound.repository;

import com.mobile.buddybound.model.entity.Account;
import com.mobile.buddybound.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByEmailAndVerificationCode(String email, String verificationCode);
}
