package ua.kpi.ivanka.marketplace.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.kpi.ivanka.marketplace.domain.model.Category;
import ua.kpi.ivanka.marketplace.dto.entity.CategoryDTO;
import ua.kpi.ivanka.marketplace.dto.request.CategoryCreateDTO;
import ua.kpi.ivanka.marketplace.dto.request.CategoryUpdateDTO;
import ua.kpi.ivanka.marketplace.web.exception.ResourceNotFoundException;
import ua.kpi.ivanka.marketplace.service.CategoryService;
import ua.kpi.ivanka.marketplace.service.mapper.CategoryMapper;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper mapper;
    private final Map<UUID, Category> categories = new ConcurrentHashMap<>();

    @Override
    public CategoryDTO create(CategoryCreateDTO dto) {
        Category category = mapper.toEntity(dto);
        category.setId(UUID.randomUUID());
        categories.put(category.getId(), category);
        log.info("Created category {}", category.getName());
        return mapper.toDto(category);
    }

    @Override
    public List<CategoryDTO> list() {
        return categories.values().stream().map(mapper::toDto).toList();
    }

    @Override
    public CategoryDTO get(UUID id) {
        Category category = categories.get(id);
        if (category == null)
            throw new ResourceNotFoundException("Category with ID " + id + " not found");
        return mapper.toDto(category);
    }

    @Override
    public CategoryDTO update(UUID id, CategoryUpdateDTO dto) {
        Category category = categories.get(id);
        if (category == null)
            throw new ResourceNotFoundException("Category with ID " + id + " not found");
        mapper.updateEntity(category, dto);
        return mapper.toDto(category);
    }

    @Override
    public void delete(UUID id) {
        categories.remove(id);
        log.info("Deleted category {}", id);
    }
}
