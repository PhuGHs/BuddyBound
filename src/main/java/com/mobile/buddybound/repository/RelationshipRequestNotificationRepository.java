package com.mobile.buddybound.repository;

import com.mobile.buddybound.model.entity.Notification;
import com.mobile.buddybound.model.entity.RelationshipRequestNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelationshipRequestNotificationRepository extends JpaRepository<RelationshipRequestNotification, Long> {
    List<RelationshipRequestNotification> findNotificationByRecipient_IdOrderByCreatedAtDesc(Long recipientId);
}
