package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFountException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.mapper = mapper;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Creates a new post.
     *
     * @param postDto The post data transfer object.
     * @return The created post DTO.
     */
    @Override
    public PostDto createPost(PostDto postDto) {
        // Fetch category by ID
        Category category = categoryRepository.findById(postDto.getCategoryID())
                .orElseThrow(() -> new ResourceNotFountException("Category", "id", postDto.getCategoryID()));

        // Convert DTO to entity
        Post post = mapToEntity(postDto);
        post.setCategory(category);

        // Save post
        Post newPost = postRepository.save(post);

        return mapToDTO(newPost);
    }

    /**
     * Retrieves all posts with pagination and sorting.
     *
     * @param pageNo   The page number.
     * @param pageSize The size of the page.
     * @param sortBy   The field to sort by.
     * @param sortDir  The direction of sorting.
     * @return The paginated response containing posts.
     */
    @Override
    public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);

        List<PostDto> content = posts.getContent()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return new PostResponse(
                content,
                posts.getNumber(),
                posts.getSize(),
                posts.getTotalElements(),
                posts.getTotalPages(),
                posts.isLast()
        );
    }

    /**
     * Retrieves a post by ID.
     *
     * @param id The post ID.
     * @return The post DTO.
     */
    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFountException("Post", "id", id));
        return mapToDTO(post);
    }

    /**
     * Updates a post by ID.
     *
     * @param postDto The updated post data.
     * @param id      The post ID.
     * @return The updated post DTO.
     */
    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        // Find the post by ID
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFountException("Post", "id", id));

        // Fetch category
        Category category = categoryRepository.findById(postDto.getCategoryID())
                .orElseThrow(() -> new ResourceNotFountException("Category", "id", postDto.getCategoryID()));

        // Update post fields
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post.setCategory(category);

        Post updatedPost = postRepository.save(post);
        return mapToDTO(updatedPost);
    }

    /**
     * Deletes a post by ID.
     *
     * @param id The post ID.
     */
    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFountException("Post", "id", id));

        postRepository.delete(post);
    }

    /**
     * Retrieves posts by category ID.
     *
     * @param categoryId The category ID.
     * @return A list of post DTOs belonging to the specified category.
     */
    @Override
    public List<PostDto> getPostsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFountException("Category", "id", categoryId));

        List<Post> posts = postRepository.findByCategoryId(categoryId);

        return posts.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Converts a Post entity to a PostDto.
     *
     * @param post The post entity.
     * @return The post DTO.
     */
    private PostDto mapToDTO(Post post) {
        return mapper.map(post, PostDto.class);
    }

    /**
     * Converts a PostDto to a Post entity.
     *
     * @param postDto The post DTO.
     * @return The post entity.
     */
    private Post mapToEntity(PostDto postDto) {
        return mapper.map(postDto, Post.class);
    }
}
