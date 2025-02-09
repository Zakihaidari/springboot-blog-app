package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.exception.ResourceNotFountException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CommentServiceImp implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentServiceImp(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = maptoEntity(commentDto);

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFountException("post", "id", postId));

        comment.setPost(post);

        Comment saveComment = commentRepository.save(comment);
        return mapToDto(saveComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFountException("post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFountException("Comment", "id", commentId));

        if (!Objects.equals(comment.getPost().getId(), post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFountException("post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFountException("Comment", "id", commentId));

        if (!Objects.equals(comment.getPost().getId(), post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updatedComment = commentRepository.save(comment);
        return mapToDto(updatedComment);

    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFountException("post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFountException("Comment", "id", commentId));

        if (!Objects.equals(comment.getPost().getId(), post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        commentRepository.delete(comment);
    }


    private CommentDto mapToDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());
        return commentDto;
    }

    private Comment maptoEntity(CommentDto commentDto) {
        Comment comment = new Comment();

        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        return comment;
    }
}
