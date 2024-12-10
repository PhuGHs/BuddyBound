package com.mobile.buddybound.service.impl;

import com.mobile.buddybound.exception.BadRequestException;
import com.mobile.buddybound.exception.CustomAccessDeniedHandler;
import com.mobile.buddybound.exception.NotFoundException;
import com.mobile.buddybound.model.constants.ImageDirectory;
import com.mobile.buddybound.model.dto.AccessRequest;
import com.mobile.buddybound.model.dto.NotificationData;
import com.mobile.buddybound.model.dto.PostCreateDto;
import com.mobile.buddybound.model.dto.PostDto;
import com.mobile.buddybound.model.entity.*;
import com.mobile.buddybound.model.enumeration.NotificationType;
import com.mobile.buddybound.model.response.ApiResponse;
import com.mobile.buddybound.model.response.ApiResponseStatus;
import com.mobile.buddybound.pattern.CoR.GroupPermissionHandler;
import com.mobile.buddybound.pattern.CoR.PermissionHandler;
import com.mobile.buddybound.pattern.CoR.PostVisibilityHandler;
import com.mobile.buddybound.pattern.CoR.RelationshipHandler;
import com.mobile.buddybound.pattern.factory_method.NotificationFactory;
import com.mobile.buddybound.pattern.factory_method.NotificationFactoryProvider;
import com.mobile.buddybound.repository.*;
import com.mobile.buddybound.service.ImageService;
import com.mobile.buddybound.service.NotificationService;
import com.mobile.buddybound.service.PostService;
import com.mobile.buddybound.service.UserService;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final ImageService imageService;
    private final PostMapper postMapper;
    private final MemberRepository memberRepository;
    private final PostVisibilityRepository postVisibilityRepository;
    private final UserService userService;
    private final GroupRepository groupRepository;
    private final static String baseUrl = ImageDirectory.POST_PREFIX;
    private final NotificationService notificationService;

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
        List<Long> newIds = new ArrayList<>(dto.getViewerIds());
        newIds.add(currentUserId);

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

        if (Objects.nonNull(image)) {
            CompletableFuture<Image> imageFuture = CompletableFuture.supplyAsync(() -> imageService.uploadImageAsync(image, baseUrl));
            post.setImage(imageFuture.join());
        }

        Post finalPost = post;
        List<PostVisibility> viewers = newIds.stream()
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

        //send notification
        for (Long id : dto.getViewerIds()) {
            NotificationData data = NotificationData.builder()
                    .senderId(currentUserId)
                    .recipientId(id)
                    .referenceId(post.getId())
                    .postTitle(post.getNote())
                    .groupName(post.getGroup().getGroupName())
                    .build();
            notificationService.sendNotification(NotificationType.GROUP_POST, data);
        }

        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "created post", postMapper.toDto(post)));
    }

    @Override
    public ResponseEntity<?> getPostDetail(Long id) {
        Long currentUserId = userService.getCurrentLoggedInUser().getId();
        Post post = this.getPost(id);
        PermissionHandler permissionHandler = this.createPermissionChain();
        AccessRequest request = AccessRequest.builder()
                .postId(post.getId())
                .ownerId(post.getMember().getUser().getId())
                .groupId(post.getGroup().getId())
                .viewerId(currentUserId)
                .build();
        if (!permissionHandler.checkAccess(request)) {
            throw new BadRequestException("You are not permitted to access this resource");
        }
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Get post", postMapper.toDto(post)));
    }

    @Override
    public ResponseEntity<?> getAllPosts(Long groupId, Pageable pageable) {
        var currentUserId = userService.getCurrentLoggedInUser().getId();
        Page<PostDto> posts = postRepository.getViewablePostsInGroup(groupId, currentUserId, pageable).map(postMapper::toDto);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "get all posts", posts));
    }

    @Override
    public Post getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post not found"));
    }

    private PermissionHandler createPermissionChain() {
        RelationshipHandler relationshipHandler = new RelationshipHandler(userService);
        GroupPermissionHandler groupPermissionHandler = new GroupPermissionHandler(memberRepository);
        PostVisibilityHandler postVisibilityHandler = new PostVisibilityHandler(postVisibilityRepository);

        relationshipHandler.setNext(groupPermissionHandler);
        groupPermissionHandler.setNext(postVisibilityHandler);

        return relationshipHandler;
    }
}
