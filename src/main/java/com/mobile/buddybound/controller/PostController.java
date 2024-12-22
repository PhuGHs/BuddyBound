package com.mobile.buddybound.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobile.buddybound.model.dto.PostCreateDto;
import com.mobile.buddybound.model.dto.PostDto;
import com.mobile.buddybound.model.entity.Post;
import com.mobile.buddybound.model.response.ApiResponse;
import com.mobile.buddybound.model.response.ApiResponseStatus;
import com.mobile.buddybound.service.PostService;
import com.mobile.buddybound.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/posts")
@SecurityRequirement(name = "bearerAuth")
public class PostController {
    private final PostService postService;
    private final UserService userService;

    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> createPost(@RequestPart(value = "image", required = false) MultipartFile image, @RequestPart(name = "postData", required = true) String postDataString) {
        ObjectMapper mapper = new ObjectMapper();
        PostCreateDto postData;
        try {
            postData = mapper.readValue(postDataString, PostCreateDto.class);
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().build();
        }
        var user = userService.getCurrentLoggedInUser();
        var dto = postService.createPost(user, postData, image);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Create post", dto));
    }

    @GetMapping("/get-all-pagination")
    public ResponseEntity<?> getAllPosts(@RequestParam(name = "groupId") @NotNull Long groupId, @PageableDefault(page = 0, size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return postService.getAllPosts(groupId, pageable);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAll(@RequestParam(name = "groupId") @NotNull Long groupId, @RequestParam(name = "isExpired", required = false) Boolean isExpired) {
        var currentUserId = userService.getCurrentLoggedInUser().getId();
        var dtoList = postService.getAllPostsWithoutPagination(currentUserId, groupId, isExpired);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Get posts", dtoList));
    }

    @GetMapping("/get-detail")
    public ResponseEntity<?> getPostDetails(@RequestParam(name = "postId") @NotNull(message = "The postId is required") Long postId) {
        var dto = postService.getPostDetail(postId);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Get post details", dto));
    }

    @GetMapping("/get-user-posts")
    public ResponseEntity<?> getUserPosts(@RequestParam(required = false) Map<String, String> params) {
        var dtoList = postService.getUserPosts();
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Get user posts", dtoList));
    }
}
