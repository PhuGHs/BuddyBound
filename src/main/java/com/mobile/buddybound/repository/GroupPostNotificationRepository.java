package com.mobile.buddybound.repository;

import com.mobile.buddybound.model.entity.GroupPostNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupPostNotificationRepository extends JpaRepository<GroupPostNotification, Long> {
    List<GroupPostNotification> findNotificationByRecipient_IdOrderByCreatedAtDesc(Long recipientId);
}
