package ua.kpi.ivanka.marketplace.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.kpi.ivanka.marketplace.domain.model.Category;
import ua.kpi.ivanka.marketplace.dto.CategoryDTO;
import ua.kpi.ivanka.marketplace.dto.request.CategoryCreateDTO;
import ua.kpi.ivanka.marketplace.dto.request.CategoryUpdateDTO;
import ua.kpi.ivanka.marketplace.service.CategoryService;
import ua.kpi.ivanka.marketplace.service.exception.CategoryNotFoundException;
import ua.kpi.ivanka.marketplace.service.mapper.CategoryMapper;
import ua.kpi.ivanka.marketplace.util.IdGenerator;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper mapper;
    private final Map<UUID, Category> categories = new ConcurrentHashMap<>();

    {
        Category cat1 = Category.builder()
                .id(IdGenerator.generate())
                .name("Cosmic Accessories")
                .build();

        Category cat2 = Category.builder()
                .id(IdGenerator.generate())
                .name("Galactic Food")
                .build();

        categories.put(cat1.getId(), cat1);
        categories.put(cat2.getId(), cat2);

        log.info("Initialized mock category store with {} items", categories.size());
    }

    @Override
    public CategoryDTO createCategory(CategoryCreateDTO dto) {
        Category category = mapper.toCategory(dto);
        category.setId(IdGenerator.generate());
        categories.put(category.getId(), category);
        log.info("Created category {}", category.getName());
        return mapper.toCategoryDTO(category);
    }

    @Override
    public List<CategoryDTO> listCategories() {
        log.debug("Listing all categories (count={})", categories.size());
        return categories.values().stream()
                .map(mapper::toCategoryDTO)
                .toList();
    }

    @Override
    public CategoryDTO getCategory(UUID id) {
        Category category = categories.get(id);
        if (category == null) {
            log.warn("Category with ID {} not found", id);
            throw new CategoryNotFoundException(id);
        }
        log.debug("Retrieved category {} (ID={})", category.getName(), id);
        return mapper.toCategoryDTO(category);
    }

    @Override
    public CategoryDTO updateCategory(UUID id, CategoryUpdateDTO dto) {
        Category category = categories.get(id);
        if (category == null) {
            log.warn("Cannot update — category with ID {} not found", id);
            throw new CategoryNotFoundException(id);
        }

        mapper.updateCategory(category, dto);
        log.info("Updated category {} (ID={})", category.getName(), id);
        return mapper.toCategoryDTO(category);
    }

    @Override
    public void deleteCategory(UUID id) {
        if (categories.remove(id) != null) {
            log.info("Deleted category {}", id);
        } else {
            log.debug("Category with ID {} already missing (delete is idempotent)", id);
        }
    }
}
