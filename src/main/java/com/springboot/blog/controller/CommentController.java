package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")  // Base URL: http://localhost:8080/api
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * Create a new comment for a specific post
     * @param postId - ID of the post to which the comment belongs
     * @param commentDto - Comment data
     * @return Created comment
     *
     * POST Request: http://localhost:8080/api/posts/1/comments
     */
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@Valid @PathVariable(value = "postId") Long postId,
                                                    @Valid @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    /**
     * Get all comments for a specific post
     * @param postId - ID of the post
     * @return List of comments for the post
     *
     * GET Request: http://localhost:8080/api/posts/1/comments
     */
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable(value = "postId") Long postId) {
        return commentService.getCommentsByPostId(postId);
    }

    /**
     * Get a specific comment by its ID for a given post
     * @param postId - ID of the post
     * @param commentId - ID of the comment
     * @return Comment details
     *
     * GET Request: http://localhost:8080/api/posts/1/comments/2
     */
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Long postId, @PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.getCommentById(postId, commentId));
    }

    /**
     * Update a specific comment by its ID for a given post
     * @param postId - ID of the post
     * @param commentId - ID of the comment
     * @param commentDto - Updated comment data
     * @return Updated comment details
     *
     * PUT Request: http://localhost:8080/api/posts/1/comments/2
     */
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long postId,
                                                    @PathVariable Long commentId,
                                                    @Valid @RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentService.updateComment(postId, commentId, commentDto));
    }

    /**
     * Delete a specific comment by its ID for a given post
     * @param postId - ID of the post
     * @param commentId - ID of the comment
     * @return Success message
     *
     * DELETE Request: http://localhost:8080/api/posts/1/comments/2
     */
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        commentService.deleteComment(postId, commentId);
        return ResponseEntity.ok("Comment deleted successfully");
    }
}
