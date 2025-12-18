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
import ua.kpi.ivanka.marketplace.dto.request.ProductCreateDTO;
import ua.kpi.ivanka.marketplace.dto.request.ProductUpdateDTO;
import ua.kpi.ivanka.marketplace.repository.CategoryRepository;
import ua.kpi.ivanka.marketplace.repository.ProductRepository;
import ua.kpi.ivanka.marketplace.repository.entity.CategoryEntity;
import ua.kpi.ivanka.marketplace.repository.entity.ProductEntity;

import java.math.BigDecimal;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Product Controller Integration Tests")
class ProductControllerIT extends AbstractIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    private CategoryEntity testCategory;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        categoryRepository.deleteAll();

        testCategory = categoryRepository.save(CategoryEntity.builder()
                .name("Space Toys")
                .description("Toys for space cats")
                .build());
    }

    @Test
    @SneakyThrows
    @DisplayName("Should create product and persist in DB")
    void shouldCreateProduct() {
        ProductCreateDTO dto = ProductCreateDTO.builder()
                .name("Galaxy Laser Pointer")
                .price(new BigDecimal("50.00"))
                .description("Red dot")
                .categoryId(testCategory.getPublicId())
                .build();

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Galaxy Laser Pointer"));
    }

    @Test
    @SneakyThrows
    @DisplayName("Should get all products")
    void shouldGetAllProducts() {
        createProductEntity("Star Ball", 10.0);
        createProductEntity("Galaxy Mouse", 20.0);

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @SneakyThrows
    @DisplayName("Should update product")
    void shouldUpdateProduct() {
        ProductEntity saved = createProductEntity("Old Galaxy Name", 100.0);

        ProductUpdateDTO updateDto = ProductUpdateDTO.builder()
                .name("New Galaxy Name")
                .price(new BigDecimal("200.00"))
                .description("Updated desc")
                .categoryId(testCategory.getPublicId())
                .build();

        mockMvc.perform(put("/api/v1/products/{id}", saved.getPublicId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Galaxy Name"))
                .andExpect(jsonPath("$.price").value(200.0));
    }

    @Test
    @SneakyThrows
    @DisplayName("Should delete product")
    void shouldDeleteProduct() {
        ProductEntity saved = createProductEntity("Comet To Delete", 5.0);

        mockMvc.perform(delete("/api/v1/products/{id}", saved.getPublicId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/products/{id}", saved.getPublicId()))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    @DisplayName("Should return 400 Bad Request when product name is invalid")
    void shouldReturn400OnInvalidName() {
        ProductCreateDTO invalidDto = ProductCreateDTO.builder()
                .name("No")
                .price(new BigDecimal("10.00"))
                .categoryId(UUID.randomUUID())
                .build();

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").exists());
    }

    @Test
    @SneakyThrows
    @DisplayName("Should search products by query")
    void shouldSearchProducts() {
        createProductEntity("Galaxy Phone", 500.0);
        createProductEntity("Star Wars Lego", 100.0);
        createProductEntity("Ordinary Spoon", 5.0);

        mockMvc.perform(get("/api/v1/products/search")
                        .param("query", "Star"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Star Wars Lego"));
    }

    @Test
    @SneakyThrows
    @DisplayName("Should filter products by max price")
    void shouldFilterByMaxPrice() {
        createProductEntity("Cheap Star", 10.0);
        createProductEntity("Expensive Star", 1000.0);

        mockMvc.perform(get("/api/v1/products/search/price")
                        .param("max", "50.00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Cheap Star"));
    }

    @Test
    @SneakyThrows
    @DisplayName("Should get products by category")
    void shouldGetByCategory() {
        createProductEntity("Space Food", 10.0);

        CategoryEntity otherCat = categoryRepository.save(CategoryEntity.builder().name("Earth").build());
        productRepository.save(ProductEntity.builder().name("Earth Food").price(BigDecimal.TEN).category(otherCat).build());

        mockMvc.perform(get("/api/v1/products/category/{id}", testCategory.getPublicId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Space Food"));
    }

    @Test
    @SneakyThrows
    @DisplayName("Should call report endpoints")
    void shouldGetReports() {
        mockMvc.perform(get("/api/v1/products/reports/sales")).andExpect(status().isOk());
        mockMvc.perform(get("/api/v1/products/reports/popular")).andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @DisplayName("Should return 404 when getting non-existent product")
    void shouldReturn404ForMissingProduct() {
        mockMvc.perform(get("/api/v1/products/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").exists());
    }

    private ProductEntity createProductEntity(String name, double price) {
        return productRepository.save(ProductEntity.builder()
                .name(name)
                .price(BigDecimal.valueOf(price))
                .category(testCategory)
                .build());
    }
}