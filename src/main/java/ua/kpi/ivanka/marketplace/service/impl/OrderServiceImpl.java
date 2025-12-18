package ua.kpi.ivanka.marketplace.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kpi.ivanka.marketplace.dto.OrderDTO;
import ua.kpi.ivanka.marketplace.dto.request.OrderCreateDTO;
import ua.kpi.ivanka.marketplace.dto.request.OrderItemRequestDTO;
import ua.kpi.ivanka.marketplace.repository.OrderRepository;
import ua.kpi.ivanka.marketplace.repository.ProductRepository;
import ua.kpi.ivanka.marketplace.repository.entity.OrderEntity;
import ua.kpi.ivanka.marketplace.repository.entity.OrderItemEntity;
import ua.kpi.ivanka.marketplace.repository.entity.ProductEntity;
import ua.kpi.ivanka.marketplace.repository.exception.CosmicPersistenceException;
import ua.kpi.ivanka.marketplace.service.OrderService;
import ua.kpi.ivanka.marketplace.service.exception.OrderNotFoundException;
import ua.kpi.ivanka.marketplace.service.exception.ProductNotFoundException;
import ua.kpi.ivanka.marketplace.service.mapper.OrderMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper mapper;

    @Override
    @Transactional
    public OrderDTO createOrder(OrderCreateDTO dto) {
        log.info("Processing new order with {} items", dto.getItems().size());
        try {
            OrderEntity order = OrderEntity.builder()
                    .status("NEW")
                    .totalPrice(BigDecimal.ZERO)
                    .build();

            BigDecimal total = BigDecimal.ZERO;
            for (OrderItemRequestDTO itemDto : dto.getItems()) {
                ProductEntity product = productRepository.findByNaturalId(itemDto.getProductId())
                        .orElseThrow(() -> new ProductNotFoundException(itemDto.getProductId()));

                OrderItemEntity orderItem = OrderItemEntity.builder()
                        .product(product)
                        .quantity(itemDto.getQuantity())
                        .priceAtOrder(product.getPrice())
                        .build();

                order.addItem(orderItem);
                BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity()));
                total = total.add(itemTotal);
            }

            order.setTotalPrice(total);
            OrderEntity savedOrder = orderRepository.save(order);
            log.info("Order placed successfully: {}", savedOrder.getPublicId());

            return mapper.toDTO(savedOrder);

        } catch (ProductNotFoundException e) {
            throw e;
        } catch (Exception ex) {
            log.error("Failed to place order", ex);
            throw new CosmicPersistenceException("Order placement failed", ex);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDTO> getAllOrders() {
        return mapper.toDTOs(orderRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDTO getOrder(UUID id) {
        return orderRepository.findByNaturalId(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDTO> getOrdersByStatus(String status) {
        log.info("Fetching orders status: {}", status);
        return mapper.toDTOs(orderRepository.findByStatus(status));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDTO> getHighValueOrders(BigDecimal minAmount) {
        log.info("Fetching orders with total price > {}", minAmount);
        return mapper.toDTOs(orderRepository.findHighValueOrders(minAmount));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDTO> getOrdersCreatedAfter(LocalDateTime date) {
        log.info("Fetching orders created after: {}", date);
        return mapper.toDTOs(orderRepository.findByCreatedAtAfter(date));
    }
}