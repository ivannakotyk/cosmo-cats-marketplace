package ua.kpi.ivanka.marketplace.service.mapper;

import org.mapstruct.*;
import ua.kpi.ivanka.marketplace.domain.model.Cart;
import ua.kpi.ivanka.marketplace.dto.entity.CartDTO;
import ua.kpi.ivanka.marketplace.dto.request.CartCreateDTO;
import ua.kpi.ivanka.marketplace.dto.request.CartUpdateDTO;

@Mapper(componentModel = "spring")
public interface CartMapper {
    Cart toEntity(CartCreateDTO dto);
    CartDTO toDto(Cart entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Cart entity, CartUpdateDTO dto);
}
