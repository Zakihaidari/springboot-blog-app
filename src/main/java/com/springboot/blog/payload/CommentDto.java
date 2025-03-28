package com.springboot.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "CommentDto Model Information")
public class CommentDto {

    @Schema(description = "Comment ID")
    private Long id;

    @Schema(description = "Name of the commenter")
    @NotEmpty(message = "Name should not be empty")
    private String name;

    @Schema(description = "Email of the commenter")
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;

    @Schema(description = "Comment content")
    @NotEmpty
    @Size(min = 10, message = "The body should have at least 10 characters")
    private String body;
}
