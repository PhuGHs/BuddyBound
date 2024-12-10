package com.mobile.buddybound.repository;

import com.mobile.buddybound.model.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findNotificationByRecipient_IdOrderByCreatedAtDesc(Long recipientId);
}
