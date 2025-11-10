package ua.kpi.ivanka.marketplace.service;

import ua.kpi.ivanka.marketplace.dto.OrderDTO;
import ua.kpi.ivanka.marketplace.dto.request.OrderCreateDTO;
import ua.kpi.ivanka.marketplace.dto.request.OrderUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    OrderDTO createOrder(OrderCreateDTO dto);

    List<OrderDTO> listOrders();

    OrderDTO getOrder(UUID id);

    OrderDTO updateOrder(UUID id, OrderUpdateDTO dto);

    void deleteOrder(UUID id);
}
