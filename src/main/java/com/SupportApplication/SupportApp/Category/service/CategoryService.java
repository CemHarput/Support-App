package com.SupportApplication.SupportApp.Category.service;

import com.SupportApplication.SupportApp.Category.dto.AddCategoryRequestDTO;
import com.SupportApplication.SupportApp.Category.dto.CategoryDTO;
import com.SupportApplication.SupportApp.Category.model.Category;
import com.SupportApplication.SupportApp.Category.repository.CategoryRepository;

import com.SupportApplication.SupportApp.Exception.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Long createCategory(AddCategoryRequestDTO addCategoryRequestDTO){
        Category category = new Category();
        category.setName(addCategoryRequestDTO.name());
        categoryRepository.save(category);
        return category.getId();
    }
    private Category findCategoryByName(String categoryName) {
        return categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with name: " + categoryName));
    }
    public CategoryDTO getCategoryByName(String categoryName) {
        Category category = findCategoryByName(categoryName);
        return CategoryDTO.convertFromCategory(category);
    }
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryDTO::convertFromCategory)
                .toList();
    }

}
