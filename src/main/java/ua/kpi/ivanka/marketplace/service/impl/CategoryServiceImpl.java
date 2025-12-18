package ua.kpi.ivanka.marketplace.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kpi.ivanka.marketplace.dto.CategoryDTO;
import ua.kpi.ivanka.marketplace.repository.CategoryRepository;
import ua.kpi.ivanka.marketplace.repository.entity.CategoryEntity;
import ua.kpi.ivanka.marketplace.repository.exception.CosmicPersistenceException;
import ua.kpi.ivanka.marketplace.service.CategoryService;
import ua.kpi.ivanka.marketplace.service.exception.CategoryByNameNotFoundException;
import ua.kpi.ivanka.marketplace.service.exception.CategoryNotFoundException;
import ua.kpi.ivanka.marketplace.service.mapper.CategoryMapper;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'API')")
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'API')")
    public CategoryDTO getCategory(UUID id) {
        return categoryRepository.findByNaturalId(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'API')")
    public List<CategoryDTO> searchCategories(String keyword) {
        log.info("Searching categories by description: {}", keyword);
        if (keyword == null || keyword.isBlank()) {
            return getAllCategories();
        }
        return categoryRepository.searchByDescription(keyword).stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'API')")
    public CategoryDTO createCategory(CategoryDTO dto) {
        log.info("Creating category: {}", dto.getName());
        if (categoryRepository.findByName(dto.getName()).isPresent()) {
            throw new CosmicPersistenceException("Category with name '" + dto.getName() + "' already exists");
        }

        try {
            CategoryEntity entity = mapper.toEntity(dto);
            CategoryEntity saved = categoryRepository.save(entity);
            return mapper.toDTO(saved);
        } catch (Exception ex) {
            log.error("Error creating category", ex);
            throw new CosmicPersistenceException("Failed to create category", ex);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'API')")
    public CategoryDTO getCategoryByName(String name) {
        log.info("Fetching category by name: {}", name);
        return categoryRepository.findByName(name)
                .map(mapper::toDTO)
                .orElseThrow(() -> new CategoryByNameNotFoundException(name));
    }
}