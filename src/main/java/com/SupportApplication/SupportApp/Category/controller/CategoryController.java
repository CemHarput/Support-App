package com.SupportApplication.SupportApp.Category.controller;

import com.SupportApplication.SupportApp.Category.dto.AddCategoryRequestDTO;
import com.SupportApplication.SupportApp.Category.dto.CategoryDTO;
import com.SupportApplication.SupportApp.Category.service.CategoryService;
import com.SupportApplication.SupportApp.Common.DTO.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@Tag(name = "Category Controller", description = "Category management endpoints")
@RequestMapping("/api/v1/")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/categories")
    public ResponseEntity<ApiResponse> createCategory(@RequestBody AddCategoryRequestDTO addCategoryRequestDTO) {
        try {
            logger.debug("createCategory is started");
            Long categoryId = categoryService.createCategory(addCategoryRequestDTO);
            logger.info("Category created successfully with ID: {}", categoryId);

            ApiResponse response = new ApiResponse("Category created with ID: " + categoryId, HttpStatus.CREATED.value());
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            logger.error("Unexpected error occurred while creating category: {}", e.getMessage(), e);
            ApiResponse error = new ApiResponse("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/categories/name/{name}")
    public ResponseEntity<CategoryDTO> getCategoryByName(@PathVariable String name) {
        CategoryDTO categoryDTO = categoryService.getCategoryByName(name);
        return ResponseEntity.ok(categoryDTO);
    }
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categoryList = categoryService.getAllCategories();
        return ResponseEntity.ok(categoryList);
    }

}
