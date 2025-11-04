package ua.kpi.ivanka.marketplace.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.kpi.ivanka.marketplace.domain.model.Order;
import ua.kpi.ivanka.marketplace.dto.entity.OrderDTO;
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
    public OrderDTO create(OrderCreateDTO dto) {
        Order order = mapper.toEntity(dto);
        order.setId(IdGenerator.generate());
        order.setCreatedAt(LocalDateTime.now());
        store.put(order.getId(), order);
        log.info("Created new order {} for product {}", order.getId(), order.getProductId());
        return mapper.toDto(order);
    }

    @Override
    public List<OrderDTO> list() {
        return store.values().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public OrderDTO get(UUID id) {
        Order order = store.get(id);
        if (order == null) throw new OrderNotFoundException(id);
        return mapper.toDto(order);
    }

    @Override
    public OrderDTO update(UUID id, OrderUpdateDTO dto) {
        Order order = store.get(id);
        if (order == null) throw new OrderNotFoundException(id);
        mapper.updateEntity(order, dto);
        log.info("Updated order {} (status={})", id, order.getStatus());
        return mapper.toDto(order);
    }

    @Override
    public void delete(UUID id) {
        if (store.remove(id) != null) {
            log.info("Deleted order with ID {}", id);
        } else {
            log.debug("Order with ID {} already missing (delete is idempotent)", id);
        }
    }
}
