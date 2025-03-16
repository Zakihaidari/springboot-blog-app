package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")  // Base URL: http://localhost:8080/api/post
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     * Create a new post (Only ADMIN can create)
     * @param postDto - Post data
     * @return Created Post
     *
     * POST Request: http://localhost:8080/api/post
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    /**
     * Get all posts with pagination and sorting
     * @param pageNo - Page number (default: 0)
     * @param pageSize - Number of posts per page (default: 10)
     * @param sortBy - Field to sort by (default: id)
     * @param sortDir - Sort direction (asc/desc)
     * @return Paginated list of posts
     *
     * GET Request: http://localhost:8080/api/post?pageNo=0&pageSize=5&sortBy=title&sortDir=asc
     */
    @GetMapping
    public PostResponse getAllPost(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        return postService.getAllPost(pageNo, pageSize, sortBy, sortDir);
    }

    /**
     * Get a post by its ID
     * @param id - Post ID
     * @return Post details
     *
     * GET Request: http://localhost:8080/api/post/1
     */
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    /**
     * Update a post by ID (Only ADMIN can update)
     * @param postDto - Updated post data
     * @param id - Post ID
     * @return Updated post details
     *
     * PUT Request: http://localhost:8080/api/post/1
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id") long id) {
        PostDto postResponse = postService.updatePost(postDto, id);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    /**
     * Delete a post by ID (Only ADMIN can delete)
     * @param id - Post ID
     * @return Success message
     *
     * DELETE Request: http://localhost:8080/api/post/1
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id) {
        postService.deletePostById(id);
        return new ResponseEntity<>("Post entity is deleted successfully", HttpStatus.OK);
    }

    /**
     * Get all posts by Category ID
     * @param categoryId - Category ID
     * @return List of posts in the specified category
     *
     * GET Request: http://localhost:8080/api/post/category/2
     */
    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable("id") Long categoryId) {
        List<PostDto> postDtos = postService.getPostsByCategory(categoryId);
        return ResponseEntity.ok(postDtos);
    }
}
