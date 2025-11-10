package ua.kpi.ivanka.marketplace.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.kpi.ivanka.marketplace.domain.model.Order;
import ua.kpi.ivanka.marketplace.dto.OrderDTO;
import ua.kpi.ivanka.marketplace.dto.request.OrderCreateDTO;
import ua.kpi.ivanka.marketplace.dto.request.OrderUpdateDTO;
import ua.kpi.ivanka.marketplace.service.exception.OrderNotFoundException;
import ua.kpi.ivanka.marketplace.service.mapper.OrderMapper;
import ua.kpi.ivanka.marketplace.service.OrderService;
import ua.kpi.ivanka.marketplace.util.IdGenerator;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper mapper;
    private final Map<UUID, Order> store = new ConcurrentHashMap<>();

    {
        Order o1 = Order.builder()
                .id(IdGenerator.generate())
                .productId(UUID.randomUUID())
                .quantity(3)
                .totalPrice(new java.math.BigDecimal("39.99"))
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .build();

        store.put(o1.getId(), o1);
        log.info("Initialized mock order store with {} items", store.size());
    }

    @Override
    public OrderDTO createOrder(OrderCreateDTO dto) {
        Order order = mapper.toOrder(dto);
        order.setId(IdGenerator.generate());
        order.setCreatedAt(LocalDateTime.now());
        store.put(order.getId(), order);

        log.info("Created new order {} for product {}", order.getId(), order.getProductId());
        return mapper.toOrderDTO(order);
    }

    @Override
    public List<OrderDTO> listOrders() {
        log.debug("Listing all orders (count={})", store.size());
        return store.values().stream()
                .map(mapper::toOrderDTO)
                .toList();
    }

    @Override
    public OrderDTO getOrder(UUID id) {
        Order order = store.get(id);
        if (order == null) {
            log.warn("Order with ID {} not found", id);
            throw new OrderNotFoundException(id);
        }

        log.debug("Retrieved order {} (status={})", id, order.getStatus());
        return mapper.toOrderDTO(order);
    }

    @Override
    public OrderDTO updateOrder(UUID id, OrderUpdateDTO dto) {
        Order order = store.get(id);
        if (order == null) {
            log.warn("Cannot update — order with ID {} not found", id);
            throw new OrderNotFoundException(id);
        }

        mapper.updateOrder(order, dto);
        log.info("Updated order {} (status={})", id, order.getStatus());
        return mapper.toOrderDTO(order);
    }

    @Override
    public void deleteOrder(UUID id) {
        if (store.remove(id) != null) {
            log.info("Deleted order with ID {}", id);
        } else {
            log.debug("Order with ID {} already missing (delete is idempotent)", id);
        }
    }
}
