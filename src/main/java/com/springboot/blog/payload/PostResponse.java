package com.springboot.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Paginated Response for Blog Posts")
public class PostResponse {

    @Schema(description = "List of posts")
    private List<PostDto> content;

    @Schema(description = "Current page number")
    private int pageNo;

    @Schema(description = "Number of items per page")
    private int pageSize;

    @Schema(description = "Total number of elements")
    private long totalElements;

    @Schema(description = "Total number of pages")
    private int totalPages;

    @Schema(description = "Indicator if this is the last page")
    private boolean last;
}
