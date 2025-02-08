package com.springboot.blog.service;

import com.springboot.blog.payload.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    List<CategoryDto> getAllCategories();
    CategoryDto getCategoryById(Long id);
    CategoryDto updateCategory(CategoryDto categoryDto, Long id);
    void deleteCategory(Long id);

}
