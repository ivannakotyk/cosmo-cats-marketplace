package ua.kpi.ivanka.marketplace.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import ua.kpi.ivanka.marketplace.config.MappersTestConfiguration;
import ua.kpi.ivanka.marketplace.dto.OrderDTO;
import ua.kpi.ivanka.marketplace.dto.request.OrderCreateDTO;
import ua.kpi.ivanka.marketplace.dto.request.OrderItemRequestDTO;
import ua.kpi.ivanka.marketplace.repository.OrderRepository;
import ua.kpi.ivanka.marketplace.repository.ProductRepository;
import ua.kpi.ivanka.marketplace.repository.entity.CategoryEntity;
import ua.kpi.ivanka.marketplace.repository.entity.OrderEntity;
import ua.kpi.ivanka.marketplace.repository.entity.ProductEntity;
import ua.kpi.ivanka.marketplace.service.impl.OrderServiceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {OrderServiceImpl.class})
@Import(MappersTestConfiguration.class)
@DisplayName("Order Service Unit Tests")
class OrderServiceTest {

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private OrderService orderService;

    private static final UUID PRODUCT_ID = UUID.randomUUID();
    private static final BigDecimal PRICE = new BigDecimal("100.00");

    @Test
    @DisplayName("Should create order and calculate total price correctly")
    void shouldCreateOrder() {
        ProductEntity mockProduct = ProductEntity.builder()
                .publicId(PRODUCT_ID)
                .name("Test Product")
                .price(PRICE)
                .category(CategoryEntity.builder().name("Cat").build())
                .build();

        when(productRepository.findByNaturalId(PRODUCT_ID)).thenReturn(Optional.of(mockProduct));
        when(orderRepository.save(any(OrderEntity.class))).thenAnswer(invocation -> {
            OrderEntity entity = invocation.getArgument(0);
            entity.setPublicId(UUID.randomUUID());
            entity.setStatus("NEW");
            return entity;
        });

        OrderCreateDTO request = OrderCreateDTO.builder()
                .items(List.of(
                        OrderItemRequestDTO.builder().productId(PRODUCT_ID).quantity(2).build()
                ))
                .build();

        OrderDTO result = orderService.createOrder(request);
        verify(productRepository, times(1)).findByNaturalId(PRODUCT_ID);
        verify(orderRepository, times(1)).save(any(OrderEntity.class));
        assertThat(result.getTotalPrice()).isEqualByComparingTo(new BigDecimal("200.00"));
        assertThat(result.getStatus()).isEqualTo("NEW");
    }

    @Test
    @DisplayName("Should return high value orders")
    void shouldReturnHighValueOrders() {
        // GIVEN
        BigDecimal minAmount = new BigDecimal("500.00");

        OrderEntity expensiveOrder = OrderEntity.builder()
                .publicId(UUID.randomUUID())
                .totalPrice(new BigDecimal("1000.00"))
                .status("NEW")
                .build();

        when(orderRepository.findHighValueOrders(minAmount)).thenReturn(List.of(expensiveOrder));

        // WHEN
        List<OrderDTO> result = orderService.getHighValueOrders(minAmount);

        // THEN
        verify(orderRepository).findHighValueOrders(minAmount);
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTotalPrice()).isEqualTo(new BigDecimal("1000.00"));
    }
}