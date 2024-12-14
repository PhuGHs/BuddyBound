package com.mobile.buddybound.service;

import com.mobile.buddybound.model.dto.CommentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    ResponseEntity<?> getComments(Long postId);
    ResponseEntity<?> addComment(CommentDto commentDto);
    ResponseEntity<?> deleteComment(Long commentId);
    ResponseEntity<?> updateComment(CommentDto commentDto);
}
