package com.springboot.blog.payload;

import com.springboot.blog.entity.Post;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {
    private Long id;

    @NotEmpty(message = "Name should not be empty")
    private String name;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty
    @Size(min = 10, message = "The body should have at least 10 characters")
    private String body;
    //private Post post;
}
