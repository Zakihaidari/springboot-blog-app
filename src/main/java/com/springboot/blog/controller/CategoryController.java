package com.springboot.blog.controller;

import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")  // Base URL: http://localhost:8080/api/categories
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Create a new category (Only accessible by ADMIN)
     * @param categoryDto - Category data
     * @return Created category
     *
     * POST Request: http://localhost:8080/api/categories
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto savedCategory = categoryService.addCategory(categoryDto);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    /**
     * Get a category by its ID
     * @param id - Category ID
     * @return Category details
     *
     * GET Request: http://localhost:8080/api/categories/1
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        CategoryDto categoryDto = categoryService.getCategoryById(id);
        return ResponseEntity.ok(categoryDto);
    }

    /**
     * Get all categories
     * @return List of all categories
     *
     * GET Request: http://localhost:8080/api/categories
     */
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    /**
     * Update an existing category (Only accessible by ADMIN)
     * @param categoryDto - Updated category data
     * @param id - Category ID
     * @return Updated category details
     *
     * PUT Request: http://localhost:8080/api/categories/1
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable Long id) {
        CategoryDto category = categoryService.updateCategory(categoryDto, id);
        return ResponseEntity.ok(category);
    }

    /**
     * Delete a category (Only accessible by ADMIN)
     * @param id - Category ID
     * @return Success message
     *
     * DELETE Request: http://localhost:8080/api/categories/1
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Category deleted successfully");
    }
}
