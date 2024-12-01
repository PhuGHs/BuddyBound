package com.mobile.buddybound.repository;

import com.mobile.buddybound.model.entity.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByGroup_Id(Long id, Pageable pageable);
}
