package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.exception.ResourceNotFountException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CommentServiceImp implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ModelMapper mapper;

    public CommentServiceImp(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    /**
     * Creates a new comment for a given post.
     *
     * @param postId     The ID of the post.
     * @param commentDto The comment DTO.
     * @return The saved comment DTO.
     */
    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        // Convert DTO to Entity
        Comment comment = mapToEntity(commentDto);

        // Find the post
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFountException("Post", "id", postId));

        // Set the post for the comment
        comment.setPost(post);

        // Save the comment
        Comment savedComment = commentRepository.save(comment);

        return mapToDto(savedComment);
    }

    /**
     * Retrieves all comments associated with a specific post.
     *
     * @param postId The ID of the post.
     * @return A list of comment DTOs.
     */
    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a comment by ID for a specific post.
     *
     * @param postId    The post ID.
     * @param commentId The comment ID.
     * @return The comment DTO.
     * @throws BlogApiException If the comment does not belong to the post.
     */
    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFountException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFountException("Comment", "id", commentId));

        validateCommentBelongsToPost(comment, post);

        return mapToDto(comment);
    }

    /**
     * Updates a comment for a specific post.
     *
     * @param postId     The post ID.
     * @param commentId  The comment ID.
     * @param commentDto The updated comment details.
     * @return The updated comment DTO.
     * @throws BlogApiException If the comment does not belong to the post.
     */
    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFountException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFountException("Comment", "id", commentId));

        validateCommentBelongsToPost(comment, post);

        // Update comment fields
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updatedComment = commentRepository.save(comment);

        return mapToDto(updatedComment);
    }

    /**
     * Deletes a comment by ID for a specific post.
     *
     * @param postId    The post ID.
     * @param commentId The comment ID.
     * @throws BlogApiException If the comment does not belong to the post.
     */
    @Override
    public void deleteComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFountException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFountException("Comment", "id", commentId));

        validateCommentBelongsToPost(comment, post);

        commentRepository.delete(comment);
    }

    /**
     * Converts a Comment entity to a CommentDto.
     *
     * @param comment The comment entity.
     * @return The comment DTO.
     */
    private CommentDto mapToDto(Comment comment) {
        return mapper.map(comment, CommentDto.class);
    }

    /**
     * Converts a CommentDto to a Comment entity.
     *
     * @param commentDto The comment DTO.
     * @return The comment entity.
     */
    private Comment mapToEntity(CommentDto commentDto) {
        return mapper.map(commentDto, Comment.class);
    }

    /**
     * Validates if a comment belongs to the specified post.
     *
     * @param comment The comment to check.
     * @param post    The post to validate against.
     * @throws BlogApiException If the comment does not belong to the post.
     */
    private void validateCommentBelongsToPost(Comment comment, Post post) {
        if (!Objects.equals(comment.getPost().getId(), post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to the post");
        }
    }
}
