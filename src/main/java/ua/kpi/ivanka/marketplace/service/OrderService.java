package ua.kpi.ivanka.marketplace.service;

import ua.kpi.ivanka.marketplace.dto.OrderDTO;
import ua.kpi.ivanka.marketplace.dto.request.OrderCreateDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderDTO createOrder(OrderCreateDTO dto);
    List<OrderDTO> getAllOrders();
    OrderDTO getOrder(UUID id);

    List<OrderDTO> getOrdersByStatus(String status);
    List<OrderDTO> getHighValueOrders(BigDecimal minAmount);
    List<OrderDTO> getOrdersCreatedAfter(LocalDateTime date);
}