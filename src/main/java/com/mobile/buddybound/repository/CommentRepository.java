package com.mobile.buddybound.repository;

import com.mobile.buddybound.model.entity.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentByPost_Id(Long postId, Sort sort);
    @Query(value = "SELECT * FROM comments c WHERE c.post_id = :postId ORDER BY c.created_at ASC LIMIT 1", nativeQuery = true)
    Comment getFirstComment(Long postId);
}
