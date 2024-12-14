package com.mobile.buddybound.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mobile.buddybound.model.dto.CommentDto;
import com.mobile.buddybound.model.dto.Views;
import com.mobile.buddybound.service.CommentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/comments")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class CommentController {
    private final CommentService commentService;
    @GetMapping()
    @JsonView(Views.Read.class)
    public ResponseEntity<?> getComments(@RequestParam Long postId) {
        return commentService.getComments(postId);
    }

    @PostMapping("/add")
    @JsonView(Views.Read.class)
    public ResponseEntity<?> addComment(@JsonView(Views.Create.class) @RequestBody CommentDto comment) {
        return commentService.addComment(comment);
    }

    @DeleteMapping("/{commentId}")
    @JsonView(Views.Read.class)
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        return commentService.deleteComment(commentId);
    }

    @PutMapping("/update")
    @JsonView(Views.Read.class)
    public ResponseEntity<?> updateComment(@RequestBody @JsonView(Views.Update.class) CommentDto comment) {
        return commentService.updateComment(comment);
    }
}
