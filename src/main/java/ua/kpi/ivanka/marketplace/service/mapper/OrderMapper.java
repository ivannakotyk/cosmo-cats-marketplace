package ua.kpi.ivanka.marketplace.service.mapper;

import org.mapstruct.*;
import ua.kpi.ivanka.marketplace.domain.model.Order;
import ua.kpi.ivanka.marketplace.dto.OrderDTO;
import ua.kpi.ivanka.marketplace.dto.request.OrderCreateDTO;
import ua.kpi.ivanka.marketplace.dto.request.OrderUpdateDTO;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order toOrder(OrderCreateDTO dto);

    OrderDTO toOrderDTO(Order entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateOrder(@MappingTarget Order entity, OrderUpdateDTO dto);
}
