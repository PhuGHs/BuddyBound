package com.mobile.buddybound.repository;

import com.mobile.buddybound.model.entity.PostVisibility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostVisibilityRepository extends JpaRepository<PostVisibility, Long> {
}
