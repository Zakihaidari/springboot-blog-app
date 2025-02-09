package com.springboot.blog.payload;

import com.springboot.blog.entity.Post;
import lombok.Data;

@Data
public class CommentDto {
    private Long id;
    private String name;
    private String email;
    private String body;
    //private Post post;
}
