package ua.kpi.ivanka.marketplace.service;

import ua.kpi.ivanka.marketplace.dto.CategoryDTO;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO dto);
    List<CategoryDTO> getAllCategories();
    CategoryDTO getCategory(UUID id);
    List<CategoryDTO> searchCategories(String keyword);
    CategoryDTO getCategoryByName(String name);
}