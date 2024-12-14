package com.mobile.buddybound.repository;

import com.mobile.buddybound.model.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p WHERE p.createdAt <= :dateTime AND p.isExpired = false")
    List<Post> findByCreatedAtBeforeAndIsExpiredFalse(LocalDateTime dateTime);

    @Query("SELECT p FROM Post p LEFT JOIN PostVisibility pv ON p.id = pv.post.id WHERE p.group.id = :groupId AND p.member.user.id = :userId")
    Page<Post> getViewablePostsInGroup(Long groupId, Long userId, Pageable pageable);

    @Query("SELECT p FROM Post p LEFT JOIN PostVisibility pv ON p.id = pv.post.id WHERE p.group.id = :groupId AND p.member.user.id = :userId")
    List<Post> getViewablePostsInGroupNoPagination(Long groupId, Long userId);
}
