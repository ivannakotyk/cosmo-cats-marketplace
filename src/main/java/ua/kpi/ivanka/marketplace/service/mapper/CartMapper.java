package ua.kpi.ivanka.marketplace.service.mapper;

import org.mapstruct.*;
import ua.kpi.ivanka.marketplace.domain.model.Cart;
import ua.kpi.ivanka.marketplace.dto.CartDTO;
import ua.kpi.ivanka.marketplace.dto.request.CartCreateDTO;
import ua.kpi.ivanka.marketplace.dto.request.CartUpdateDTO;

@Mapper(componentModel = "spring")
public interface CartMapper {

    Cart toCart(CartCreateDTO dto);

    CartDTO toCartDTO(Cart entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCart(@MappingTarget Cart entity, CartUpdateDTO dto);
}
