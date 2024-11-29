package com.mobile.buddybound.service;

import com.mobile.buddybound.model.dto.MessageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface MessageService {
    ResponseEntity<?> sendAMessage(MessageDto dto);
    ResponseEntity<?> getAllGroupMessages(Long groupId);
}
