package com.springboot.blog.service;

import com.springboot.blog.payload.CommentDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface  CommentService {

    CommentDto createComment(long postId, CommentDto commentDto);

    List<CommentDto> getCommentsByPostId(Long postId);

    CommentDto getCommentById(Long postId, Long commentId);

    CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto);

    void deleteComment(Long postId, Long commentId);
}
