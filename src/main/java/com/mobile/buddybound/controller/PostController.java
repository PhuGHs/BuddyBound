package com.mobile.buddybound.controller;

import com.mobile.buddybound.model.dto.PostCreateDto;
import com.mobile.buddybound.model.dto.PostDto;
import com.mobile.buddybound.model.entity.Post;
import com.mobile.buddybound.service.PostService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/posts")
@SecurityRequirement(name = "bearerAuth")
public class PostController {
    private final PostService postService;

    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> createPost(@RequestPart("image") MultipartFile image, @RequestPart(name = "postData") PostCreateDto postData) {
        return postService.createPost(postData, image);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllPosts(@RequestParam(name = "groupId") @NotNull Long groupId, @PageableDefault(page = 0, size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return postService.getAllPosts(groupId, pageable);
    }
}
