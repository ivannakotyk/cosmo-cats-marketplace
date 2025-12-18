package ua.kpi.ivanka.marketplace.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import ua.kpi.ivanka.marketplace.config.MappersTestConfiguration;
import ua.kpi.ivanka.marketplace.dto.CategoryDTO;
import ua.kpi.ivanka.marketplace.repository.CategoryRepository;
import ua.kpi.ivanka.marketplace.repository.entity.CategoryEntity;
import ua.kpi.ivanka.marketplace.repository.exception.CosmicPersistenceException;
import ua.kpi.ivanka.marketplace.service.impl.CategoryServiceImpl;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {CategoryServiceImpl.class})
@Import(MappersTestConfiguration.class)
@DisplayName("Category Service Unit Tests")
class CategoryServiceTest {

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Test
    @DisplayName("Should create category successfully if name is unique")
    void shouldCreateCategory() {
        String name = "Unique Category";
        CategoryDTO request = CategoryDTO.builder().name(name).build();
        when(categoryRepository.findByName(name)).thenReturn(Optional.empty());
        when(categoryRepository.save(any(CategoryEntity.class))).thenAnswer(inv -> {
            CategoryEntity entity = inv.getArgument(0);
            entity.setPublicId(UUID.randomUUID());
            return entity;
        });

        CategoryDTO result = categoryService.createCategory(request);
        verify(categoryRepository).findByName(name);
        verify(categoryRepository).save(any());
        assertThat(result.getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("Should throw exception when creating duplicate category")
    void shouldThrowOnDuplicate() {
        String name = "Existing Category";
        CategoryDTO request = CategoryDTO.builder().name(name).build();
        when(categoryRepository.findByName(name)).thenReturn(Optional.of(new CategoryEntity()));
        assertThrows(CosmicPersistenceException.class, () -> categoryService.createCategory(request));
        verify(categoryRepository, never()).save(any());
    }
}