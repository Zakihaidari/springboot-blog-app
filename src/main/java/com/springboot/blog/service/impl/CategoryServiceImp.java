package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Category;
import com.springboot.blog.exception.ResourceNotFountException;
import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImp implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImp(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Adds a new category to the database.
     *
     * @param categoryDto The category data transfer object containing name and description.
     * @return The saved category as a DTO.
     */
    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        // Convert DTO to Entity
        Category category = modelMapper.map(categoryDto, Category.class);

        // Save to database
        Category savedCategory = categoryRepository.save(category);

        // Convert Entity to DTO and return
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    /**
     * Retrieves a category by its ID.
     *
     * @param id The category ID.
     * @return The corresponding category DTO.
     * @throws ResourceNotFountException if the category does not exist.
     */
    @Override
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFountException("Category", "id", id));
        return modelMapper.map(category, CategoryDto.class);
    }

    /**
     * Fetches all categories from the database.
     *
     * @return A list of category DTOs.
     */
    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing category.
     *
     * @param categoryDto The updated category details.
     * @param id          The ID of the category to update.
     * @return The updated category DTO.
     * @throws ResourceNotFountException if the category does not exist.
     */
    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFountException("Category", "id", id));

        // Update category details
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        // Save updated category
        Category updatedCategory = categoryRepository.save(category);

        return modelMapper.map(updatedCategory, CategoryDto.class);
    }

    /**
     * Deletes a category by ID.
     *
     * @param id The ID of the category to delete.
     * @throws ResourceNotFountException if the category does not exist.
     */
    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFountException("Category", "id", id));
        categoryRepository.delete(category);
    }
}
