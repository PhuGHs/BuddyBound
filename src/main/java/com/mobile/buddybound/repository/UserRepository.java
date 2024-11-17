package com.mobile.buddybound.repository;

import com.mobile.buddybound.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findById(long id);

    Optional<User> findById(Long id);
    @Override
    boolean existsById(Long aLong);

    List<User> findByFullNameContainingIgnoreCaseOrPhoneNumberContaining(String fullName, String phoneNumber);
}
