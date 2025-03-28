package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")  // Base URL: http://localhost:8080/api
@Tag(name = "Comment Management APIs", description = "APIs for managing comments on posts")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(summary = "Create a new comment", description = "Adds a comment to a specific post")
    @ApiResponse(responseCode = "201", description = "Comment successfully created")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@Valid @PathVariable(value = "postId") Long postId,
                                                    @Valid @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Get all comments for a post", description = "Retrieves all comments associated with a specific post")
    @ApiResponse(responseCode = "200", description = "Comments retrieved successfully")
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable(value = "postId") Long postId) {
        return commentService.getCommentsByPostId(postId);
    }

    @Operation(summary = "Get a specific comment by ID", description = "Retrieves a single comment by ID for a given post")
    @ApiResponse(responseCode = "200", description = "Comment retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Comment not found")
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Long postId, @PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.getCommentById(postId, commentId));
    }

    @Operation(summary = "Update a comment", description = "Updates an existing comment for a given post")
    @ApiResponse(responseCode = "200", description = "Comment updated successfully")
    @ApiResponse(responseCode = "404", description = "Comment not found")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long postId,
                                                    @PathVariable Long commentId,
                                                    @Valid @RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentService.updateComment(postId, commentId, commentDto));
    }

    @Operation(summary = "Delete a comment", description = "Deletes a comment by its ID for a given post")
    @ApiResponse(responseCode = "200", description = "Comment deleted successfully")
    @ApiResponse(responseCode = "404", description = "Comment not found")
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        commentService.deleteComment(postId, commentId);
        return ResponseEntity.ok("Comment deleted successfully");
    }
}
