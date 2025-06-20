package com.SupportApplication.SupportApp;

import com.SupportApplication.SupportApp.Category.dto.AddCategoryRequestDTO;
import com.SupportApplication.SupportApp.Category.dto.CategoryDTO;
import com.SupportApplication.SupportApp.Category.model.Category;
import com.SupportApplication.SupportApp.Category.repository.CategoryRepository;
import com.SupportApplication.SupportApp.Category.service.CategoryService;
import com.SupportApplication.SupportApp.Exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
 class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;


    @Test
    void shouldCreateCategorySuccessfully() {
        AddCategoryRequestDTO dto = new AddCategoryRequestDTO("Fitness");
        Category mockSavedCategory = new Category();
        mockSavedCategory.setName("Fitness");

        when(categoryRepository.save(any(Category.class))).thenReturn(mockSavedCategory);

        Long id = categoryService.createCategory(dto);

        assertThat(id).isNull();
        assertThat(mockSavedCategory.getName()).isEqualTo("Fitness");
        verify(categoryRepository, times(1)).save(any(Category.class));
    }
    @Test
    void shouldReturnCategoryDTO_WhenCategoryExists() {
        // arrange
        Category category = new Category("Yoga");
        when(categoryRepository.findByName("Yoga")).thenReturn(Optional.of(category));

        // act
        CategoryDTO result = categoryService.getCategoryByName("Yoga");

        // assert
        assertThat(result.name()).isEqualTo("Yoga");
        verify(categoryRepository).findByName("Yoga");
    }

    @Test
    void shouldThrowException_WhenCategoryNotExists() {
        // arrange
        when(categoryRepository.findByName("Pilates")).thenReturn(Optional.empty());

        // act + assert
        assertThatThrownBy(() -> categoryService.getCategoryByName("Pilates"))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Category not found with name: Pilates");
    }

    @Test
    void shouldReturnAllCategories() {
        Category cat1 = new Category("Crossfit");
        Category cat2 = new Category("Zumba");

        when(categoryRepository.findAll()).thenReturn(Arrays.asList(cat1, cat2));

        List<CategoryDTO> list = categoryService.getAllCategories();

        assertThat(list).hasSize(2);
        assertThat(list.get(0).name()).isEqualTo("Crossfit");
        assertThat(list.get(1).name()).isEqualTo("Zumba");
    }



}
