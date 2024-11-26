package com.mobile.buddybound.service;

import com.mobile.buddybound.model.dto.GroupDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface GroupService {
    ResponseEntity<?> createGroup(GroupDto dto);
}
