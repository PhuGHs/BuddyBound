package com.mobile.buddybound.service;

import com.mobile.buddybound.model.dto.MessagePostDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface MessageService {
    ResponseEntity<?> sendAMessage(MessagePostDto dto, List<MultipartFile> images);
    ResponseEntity<?> getAllGroupMessages(Long groupId, Pageable pageable);
}
