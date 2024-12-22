package com.mobile.buddybound.repository;

import com.mobile.buddybound.model.entity.GroupInvitationNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupInvitationNotificationRepository extends JpaRepository<GroupInvitationNotification, Long> {
    List<GroupInvitationNotification> findNotificationByRecipient_IdOrderByCreatedAtDesc(Long recipientId);
}
