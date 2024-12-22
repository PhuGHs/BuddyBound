package com.mobile.buddybound.repository;

import com.mobile.buddybound.model.entity.CommentNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentNotificationRepository extends JpaRepository<CommentNotification, Long> {
    List<CommentNotification> findNotificationByRecipient_IdOrderByCreatedAtDesc(Long recipientId);
}
