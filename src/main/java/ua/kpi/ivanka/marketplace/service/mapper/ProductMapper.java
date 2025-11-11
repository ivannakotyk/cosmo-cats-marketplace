package ua.kpi.ivanka.marketplace.service.mapper;

import org.mapstruct.*;
import ua.kpi.ivanka.marketplace.domain.model.Product;
import ua.kpi.ivanka.marketplace.dto.ProductDTO;
import ua.kpi.ivanka.marketplace.dto.request.ProductCreateDTO;
import ua.kpi.ivanka.marketplace.dto.request.ProductUpdateDTO;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toProduct(ProductCreateDTO dto);

    ProductDTO toProductDTO(Product entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProduct(@MappingTarget Product entity, ProductUpdateDTO dto);
}
