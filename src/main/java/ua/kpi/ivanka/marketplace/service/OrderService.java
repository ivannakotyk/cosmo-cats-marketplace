package ua.kpi.ivanka.marketplace.service;

import ua.kpi.ivanka.marketplace.dto.entity.OrderDTO;
import ua.kpi.ivanka.marketplace.dto.request.OrderCreateDTO;
import ua.kpi.ivanka.marketplace.dto.request.OrderUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    OrderDTO create(OrderCreateDTO dto);

    List<OrderDTO> list();

    OrderDTO get(UUID id);

    OrderDTO update(UUID id, OrderUpdateDTO dto);

    void delete(UUID id);
}
