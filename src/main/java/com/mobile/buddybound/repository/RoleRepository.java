package com.mobile.buddybound.repository;

import com.mobile.buddybound.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsByRoleName(String roleName);
    Optional<Role> findByRoleName(String roleName);
}
