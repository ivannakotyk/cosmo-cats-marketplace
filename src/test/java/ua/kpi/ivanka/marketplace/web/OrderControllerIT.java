package ua.kpi.ivanka.marketplace.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ua.kpi.ivanka.marketplace.AbstractIT;
import ua.kpi.ivanka.marketplace.dto.request.OrderCreateDTO;
import ua.kpi.ivanka.marketplace.dto.request.OrderItemRequestDTO;
import ua.kpi.ivanka.marketplace.repository.CategoryRepository;
import ua.kpi.ivanka.marketplace.repository.OrderRepository;
import ua.kpi.ivanka.marketplace.repository.ProductRepository;
import ua.kpi.ivanka.marketplace.repository.entity.CategoryEntity;
import ua.kpi.ivanka.marketplace.repository.entity.ProductEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Order Controller Integration Tests")
class OrderControllerIT extends AbstractIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    private ProductEntity product;

    @BeforeEach
    void setUp() {
        orderRepository.deleteAll();
        productRepository.deleteAll();
        categoryRepository.deleteAll();

        CategoryEntity cat = categoryRepository.save(CategoryEntity.builder()
                .name("Order Cat")
                .description("desc")
                .build());

        product = productRepository.save(ProductEntity.builder()
                .name("Expensive Stuff")
                .price(new BigDecimal("100.00"))
                .category(cat)
                .build());
    }

    @Test
    @SneakyThrows
    @DisplayName("Should create order and calculate total price correctly")
    void shouldCreateOrder() {
        OrderCreateDTO dto = OrderCreateDTO.builder()
                .items(List.of(
                        OrderItemRequestDTO.builder()
                                .productId(product.getPublicId())
                                .quantity(2)
                                .build()
                ))
                .build();

        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.totalPrice").value(200.0))
                .andExpect(jsonPath("$.status").value("NEW"));
    }

    @Test
    @SneakyThrows
    @DisplayName("Should filter orders by status")
    void shouldFilterByStatus() {
        OrderCreateDTO dto = OrderCreateDTO.builder()
                .items(List.of(OrderItemRequestDTO.builder().productId(product.getPublicId()).quantity(1).build()))
                .build();
        mockMvc.perform(post("/api/v1/orders").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dto)));

        mockMvc.perform(get("/api/v1/orders/status/{status}", "NEW"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].status").value("NEW"));
    }

    @Test
    @SneakyThrows
    @DisplayName("Should get all orders")
    void shouldGetAllOrders() {
        OrderCreateDTO dto = OrderCreateDTO.builder()
                .items(List.of(OrderItemRequestDTO.builder().productId(product.getPublicId()).quantity(1).build()))
                .build();
        mockMvc.perform(post("/api/v1/orders").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dto)));

        mockMvc.perform(get("/api/v1/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    @SneakyThrows
    @DisplayName("Should return 404 when ordering non-existent product")
    void shouldReturn404WhenProductNotFound() {
        OrderCreateDTO dto = OrderCreateDTO.builder()
                .items(List.of(
                        OrderItemRequestDTO.builder()
                                .productId(UUID.randomUUID())
                                .quantity(1)
                                .build()
                ))
                .build();

        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").exists());
    }

    @Test
    @SneakyThrows
    @DisplayName("Should return 404 when getting non-existent order")
    void shouldReturn404ForMissingOrder() {
        mockMvc.perform(get("/api/v1/orders/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").exists());
    }
}