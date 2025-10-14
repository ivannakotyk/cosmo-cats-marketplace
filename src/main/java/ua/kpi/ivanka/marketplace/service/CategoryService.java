package ua.kpi.ivanka.marketplace.service;

import ua.kpi.ivanka.marketplace.dto.entity.CategoryDTO;
import ua.kpi.ivanka.marketplace.dto.request.CategoryCreateDTO;
import ua.kpi.ivanka.marketplace.dto.request.CategoryUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryDTO create(CategoryCreateDTO dto);
    List<CategoryDTO> list();
    CategoryDTO get(UUID id);
    CategoryDTO update(UUID id, CategoryUpdateDTO dto);
    void delete(UUID id);
}
