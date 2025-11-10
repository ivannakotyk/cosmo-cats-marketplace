package ua.kpi.ivanka.marketplace.service;

import ua.kpi.ivanka.marketplace.dto.CategoryDTO;
import ua.kpi.ivanka.marketplace.dto.request.CategoryCreateDTO;
import ua.kpi.ivanka.marketplace.dto.request.CategoryUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryDTO createCategory(CategoryCreateDTO dto);
    List<CategoryDTO> listCategories();
    CategoryDTO getCategory(UUID id);
    CategoryDTO updateCategory(UUID id, CategoryUpdateDTO dto);
    void deleteCategory(UUID id);
}

