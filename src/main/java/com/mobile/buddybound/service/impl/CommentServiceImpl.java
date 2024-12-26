package com.mobile.buddybound.service.impl;

import com.mobile.buddybound.exception.BadRequestException;
import com.mobile.buddybound.exception.NotFoundException;
import com.mobile.buddybound.model.dto.AccessRequest;
import com.mobile.buddybound.model.dto.CommentDto;
import com.mobile.buddybound.model.dto.NotificationData;
import com.mobile.buddybound.model.entity.Comment;
import com.mobile.buddybound.model.entity.Member;
import com.mobile.buddybound.model.entity.Post;
import com.mobile.buddybound.model.enumeration.NotificationType;
import com.mobile.buddybound.model.response.ApiResponse;
import com.mobile.buddybound.model.response.ApiResponseStatus;
import com.mobile.buddybound.pattern.CoR.GroupPermissionHandler;
import com.mobile.buddybound.pattern.CoR.PermissionHandler;
import com.mobile.buddybound.pattern.CoR.PostVisibilityHandler;
import com.mobile.buddybound.pattern.CoR.RelationshipHandler;
import com.mobile.buddybound.repository.CommentRepository;
import com.mobile.buddybound.repository.MemberRepository;
import com.mobile.buddybound.repository.PostRepository;
import com.mobile.buddybound.service.CommentService;
import com.mobile.buddybound.service.NotificationService;
import com.mobile.buddybound.service.UserService;
import com.mobile.buddybound.service.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final UserService userService;
    private final NotificationService notificationService;

    @Override
    public ResponseEntity<?> getComments(Long postId) {
        List<Comment> comments = commentRepository.findCommentByPost_Id(postId, Sort.by("createdAt").descending());
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Get post comments", comments.stream().map(commentMapper::toDto)));
    }

    @Override
    @Transactional
    public ResponseEntity<?> addComment(CommentDto commentDto) {
        PermissionHandler permissionHandler = this.createPermissionChain();
        var currentUserId = userService.getCurrentLoggedInUser().getId();
        var post = postRepository.findById(commentDto.getPostId())
                .orElseThrow(() -> new NotFoundException("Post not found"));
        var member = memberRepository.getMemberByUser_IdAndGroup_Id(currentUserId, post.getGroup().getId())
                .orElseThrow(() -> new NotFoundException("Member not found"));
        AccessRequest request = AccessRequest.builder()
                .postId(commentDto.getPostId())
                .viewerId(currentUserId)
                .groupId(post.getGroup().getId())
                .build();
        if (!permissionHandler.checkAccess(request)) {
            throw new BadRequestException("Access denied");
        }
        Comment comment = Comment.builder()
                .content(commentDto.getContent())
                .post(post)
                .member(member)
                .build();
        comment = commentRepository.save(comment);
        if (!currentUserId.equals(post.getMember().getUser().getId())) {
            NotificationData data = NotificationData.builder()
                    .senderId(currentUserId)
                    .recipientId(post.getMember().getUser().getId())
                    .referenceId(post.getId())
                    .postTitle(post.getNote())
                    .commentContent(comment.getContent())
                    .build();
            notificationService.sendNotification(NotificationType.COMMENT, data);
        }
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Add new comment", commentMapper.toDto(comment)));
    }

    @Override
    public ResponseEntity<?> deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<?> updateComment(CommentDto commentDto) {
        PermissionHandler permissionHandler = this.createPermissionChain();
        var currentUserId = userService.getCurrentLoggedInUser().getId();
        Comment comment = commentRepository.findById(commentDto.getId())
                .orElseThrow(() -> new NotFoundException("Comment not found"));
        AccessRequest request = AccessRequest.builder()
                .postId(comment.getPost().getId())
                .viewerId(currentUserId)
                .groupId(comment.getPost().getGroup().getId())
                .build();
        if (!permissionHandler.checkAccess(request)) {
            throw new BadRequestException("Access denied");
        }
        if (!comment.getMember().getUser().getId().equals(currentUserId)) {
            throw new BadRequestException("Access denied");
        }
        comment.setContent(commentDto.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        comment = commentRepository.save(comment);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Update comment", commentMapper.toDto(comment)));
    }

    private PermissionHandler createPermissionChain() {
        RelationshipHandler relationshipHandler = new RelationshipHandler(userService);
        GroupPermissionHandler groupPermissionHandler = new GroupPermissionHandler(memberRepository);

        relationshipHandler.setNext(groupPermissionHandler);

        return relationshipHandler;
    }

}
