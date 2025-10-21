package ua.kpi.ivanka.marketplace.service.mapper;

import ua.kpi.ivanka.marketplace.domain.model.Product;
import ua.kpi.ivanka.marketplace.dto.entity.ProductDTO;
import ua.kpi.ivanka.marketplace.dto.request.ProductCreateDTO;
import ua.kpi.ivanka.marketplace.dto.request.ProductUpdateDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductCreateDTO dto);

    ProductDTO toDto(Product entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Product entity, ProductUpdateDTO dto);
}