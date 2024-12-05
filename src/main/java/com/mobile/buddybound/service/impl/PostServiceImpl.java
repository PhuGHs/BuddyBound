package com.mobile.buddybound.service.impl;

import com.mobile.buddybound.exception.NotFoundException;
import com.mobile.buddybound.model.constants.ImageDirectory;
import com.mobile.buddybound.model.dto.PostCreateDto;
import com.mobile.buddybound.model.dto.PostDto;
import com.mobile.buddybound.model.entity.*;
import com.mobile.buddybound.model.response.ApiResponse;
import com.mobile.buddybound.model.response.ApiResponseStatus;
import com.mobile.buddybound.repository.*;
import com.mobile.buddybound.service.ImageService;
import com.mobile.buddybound.service.PostService;
import com.mobile.buddybound.service.UserService;
import com.mobile.buddybound.service.mapper.LocationHistoryMapper;
import com.mobile.buddybound.service.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final ImageService imageService;
    private final PostMapper postMapper;
    private final MemberRepository memberRepository;
    private final UserService userService;
    private final GroupRepository groupRepository;
    private final static String baseUrl = ImageDirectory.POST_PREFIX;

    @Scheduled(fixedRate = 60000) // Runs every minute
    public void checkAndUpdateExpiredPosts() {
        LocalDateTime expirationThreshold = LocalDateTime.now().minusHours(24);

        List<Post> expiredPosts = postRepository.findByCreatedAtBeforeAndIsExpiredFalse(expirationThreshold);
        if (expiredPosts.isEmpty()) {
            return;
        }
        for (Post post : expiredPosts) {
            post.setExpired(true);
        }
        postRepository.saveAll(expiredPosts);
    }

    @Override
    @Transactional
    public ResponseEntity<?> createPost(PostCreateDto dto, MultipartFile image) {
        var user = userService.getCurrentLoggedInUser();
        var currentUserId = user.getId();
        var member = memberRepository.getMemberByUser_IdAndGroup_Id(currentUserId, dto.getGroupId())
                .orElseThrow(() -> new NotFoundException("Member not found"));
        var group = groupRepository.findById(dto.getGroupId())
                .orElseThrow(() -> new NotFoundException("Group not found"));
        dto.getViewerIds().add(member.getId());

        LocationHistory location = LocationHistory.builder()
                .user(user)
                .latitude(dto.getLocation().getLatitude())
                .longitude(dto.getLocation().getLongitude())
                .build();

        Post post = Post.builder()
                .member(member)
                .group(group)
                .location(location)
                .note(dto.getNote())
                .isExpired(false)
                .build();

        try {
            Post finalPost1 = post;
            imageService.uploadImage(image, baseUrl)
                    .thenAccept(url -> {
                        Image createdImage = Image.builder()
                                .imageUrl(url)
                                .build();
                        finalPost1.setImage(createdImage);
                    })
                    .exceptionally(ex -> {
                        return null;
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Post finalPost = post;
        List<PostVisibility> viewers = dto.getViewerIds().stream()
                .map(memberId -> {
                    Member viewer = memberRepository.findById(memberId).
                            orElseThrow(() -> new NotFoundException("Member not found"));
                    return PostVisibility.builder()
                            .post(finalPost)
                            .member(viewer)
                            .build();
                }).toList();

        post.setPostVisibilities(viewers);
        post = postRepository.save(post);

        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "created post", postMapper.toDto(post)));
    }

    @Override
    public ResponseEntity<?> getAllPosts(Long groupId, Pageable pageable) {
        var currentUserId = userService.getCurrentLoggedInUser().getId();
        Page<PostDto> posts = postRepository.getViewablePostsInGroup(groupId, currentUserId, pageable).map(postMapper::toDto);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "get all posts", posts));
    }

}
