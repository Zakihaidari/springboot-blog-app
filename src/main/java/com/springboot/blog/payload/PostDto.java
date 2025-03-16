package com.springboot.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
@Schema(description = "PostDto Model Information")
public class PostDto {

    private long id;

    @Schema(description = "Blog post title")
    @NotEmpty
    @Size(min = 2,message = "The title should have at least 2 characters")
    private String title;

    @Schema(description = "Blog post description")
    @NotEmpty
    @Size(min = 10, message = "The description should have at least 10 characters")
    private String description;

    @Schema(description = "Blog post content")
    @NotEmpty
    private String content;

    private Set<CommentDto> comments;

    @Schema(description = "Blog post categoryID")
    private Long categoryID;

}
