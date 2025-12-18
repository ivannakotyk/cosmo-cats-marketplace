package ua.kpi.ivanka.marketplace.service.mapper;

import org.mapstruct.*;
import ua.kpi.ivanka.marketplace.dto.OrderDTO;
import ua.kpi.ivanka.marketplace.dto.OrderItemDTO;
import ua.kpi.ivanka.marketplace.repository.entity.OrderEntity;
import ua.kpi.ivanka.marketplace.repository.entity.OrderItemEntity;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    @Mapping(target = "id", source = "publicId")
    @Mapping(target = "items", source = "items")
    OrderDTO toDTO(OrderEntity entity);

    @Mapping(target = "productId", source = "product.publicId")
    @Mapping(target = "productName", source = "product.name")
    OrderItemDTO toItemDTO(OrderItemEntity itemEntity);

    List<OrderDTO> toDTOs(List<OrderEntity> entities);
}