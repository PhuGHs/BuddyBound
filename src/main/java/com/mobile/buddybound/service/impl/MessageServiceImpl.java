package com.mobile.buddybound.service.impl;

import com.mobile.buddybound.exception.NotFoundException;
import com.mobile.buddybound.model.dto.MessageDto;
import com.mobile.buddybound.model.entity.Group;
import com.mobile.buddybound.repository.GroupRepository;
import com.mobile.buddybound.repository.MessageRepository;
import com.mobile.buddybound.service.MessageService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final GroupRepository groupRepository;

    @Override
    public ResponseEntity<?> sendAMessage(MessageDto dto) {
        if (!groupRepository.existsById(dto.getGroupId())) {
            throw new NotFoundException("Group not found");
        }

    }

    @Override
    public ResponseEntity<?> getAllGroupMessages(Long groupId) {
        return null;
    }
}
