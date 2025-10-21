package ua.kpi.ivanka.marketplace.service.mapper;

import org.mapstruct.*;
import ua.kpi.ivanka.marketplace.domain.model.Category;
import ua.kpi.ivanka.marketplace.dto.entity.CategoryDTO;
import ua.kpi.ivanka.marketplace.dto.request.CategoryCreateDTO;
import ua.kpi.ivanka.marketplace.dto.request.CategoryUpdateDTO;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CategoryCreateDTO dto);
    CategoryDTO toDto(Category entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Category entity, CategoryUpdateDTO dto);
}
