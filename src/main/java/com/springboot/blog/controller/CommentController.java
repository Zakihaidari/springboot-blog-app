package com.springboot.blog.controller;


import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") Long postId, @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);

    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable(value = "postId") Long postId) {
        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Long postId, @PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.getCommentById(postId, commentId));
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentService.updateComment(postId, commentId, commentDto));
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        commentService.deleteComment(postId, commentId);
        return ResponseEntity.ok("Comment deleted successfully");
    }

}
