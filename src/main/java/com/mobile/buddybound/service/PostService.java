package com.mobile.buddybound.service;

import com.mobile.buddybound.model.dto.PostCreateDto;
import com.mobile.buddybound.model.dto.PostDto;
import com.mobile.buddybound.model.entity.Post;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface PostService {
    ResponseEntity<?> createPost(PostCreateDto dto, MultipartFile image);
    ResponseEntity<?> getAllPosts(Long groupId);
}
