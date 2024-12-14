package com.mobile.buddybound.service;

import com.mobile.buddybound.model.dto.PostCreateDto;
import com.mobile.buddybound.model.dto.PostDto;
import com.mobile.buddybound.model.entity.Post;
import com.mobile.buddybound.model.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface PostService {
    PostDto createPost(User user, PostCreateDto dto, MultipartFile image);
    PostDto getPostDetail(Long id);
    ResponseEntity<?> getAllPosts(Long groupId, Pageable pageable);
    List<PostDto> getAllPostsWithoutPagination(Long currentUserId, Long groupId);
    Post getPost(Long postId);
}
