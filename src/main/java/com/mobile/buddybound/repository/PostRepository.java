package com.mobile.buddybound.repository;

import com.mobile.buddybound.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p WHERE p.createdAt <= :dateTime AND p.isExpired = false")
    List<Post> findByCreatedAtBeforeAndIsExpiredFalse(LocalDateTime dateTime);
}
