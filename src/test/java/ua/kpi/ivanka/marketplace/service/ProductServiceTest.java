package ua.kpi.ivanka.marketplace.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import ua.kpi.ivanka.marketplace.client.RatesClient;
import ua.kpi.ivanka.marketplace.config.MappersTestConfiguration;
import ua.kpi.ivanka.marketplace.dto.ProductDTO;
import ua.kpi.ivanka.marketplace.dto.request.ProductCreateDTO;
import ua.kpi.ivanka.marketplace.dto.request.ProductUpdateDTO;
import ua.kpi.ivanka.marketplace.service.exception.ProductNotFoundException;
import ua.kpi.ivanka.marketplace.service.impl.ProductServiceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = { ProductServiceImpl.class })
@Import(MappersTestConfiguration.class)
@DisplayName("ProductService unit tests")
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private RatesClient ratesClient;

    private ProductCreateDTO create(String name, double price) {
        return ProductCreateDTO.builder()
                .name(name)
                .price(BigDecimal.valueOf(price))
                .description("desc")
                .build();
    }

    @Test
    @DisplayName("Should create product and fetch it by ID")
    void shouldCreateAndFetch() {
        ProductDTO created = productService.createProduct(create("Cosmic Cat Bed", 99.99));
        assertThat(created.getId())
                .as("Expected product ID not to be null after creation")
                .isNotNull();

        ProductDTO fetched = productService.getProduct(created.getId());
        assertThat(fetched)
                .as(String.format("Fetched product should match the one created with ID: %s", created.getId()))
                .isEqualTo(created);
    }

    @Test
    @DisplayName("Should return non-empty product list")
    void shouldList() {
        List<ProductDTO> list = productService.listProducts();
        assertThat(list)
                .as("Expected product list not to be empty after initialization")
                .isNotEmpty();
    }

    @Test
    @DisplayName("Should update existing product fields correctly")
    void shouldUpdate() {
        ProductDTO created = productService.createProduct(create("Star Toy", 10.0));
        UUID id = created.getId();

        ProductUpdateDTO upd = ProductUpdateDTO.builder()
                .name("Star Toy PRO")
                .price(BigDecimal.valueOf(12.5))
                .description("updated")
                .build();

        ProductDTO updated = productService.updateProduct(id, upd);

        assertThat(updated.getName())
                .as(String.format("Expected updated name to be '%s'", "Star Toy PRO"))
                .isEqualTo("Star Toy PRO");

        assertThat(updated.getPrice())
                .as(String.format("Expected updated price to be %.2f", 12.5))
                .isEqualByComparingTo(BigDecimal.valueOf(12.5));

        assertThat(updated.getDescription())
                .as("Expected description to be updated")
                .isEqualTo("updated");
    }

    @Test
    @DisplayName("Should throw ProductNotFoundException for invalid ID")
    void shouldThrowNotFound() {
        UUID rnd = UUID.randomUUID();

        assertThrows(ProductNotFoundException.class,
                () -> productService.getProduct(rnd),
                String.format("Expected ProductNotFoundException when getting product with ID: %s", rnd));

        assertThrows(ProductNotFoundException.class,
                () -> productService.updateProduct(rnd, ProductUpdateDTO.builder().name("x").build()),
                String.format("Expected ProductNotFoundException when updating product with ID: %s", rnd));
    }

    @Test
    @DisplayName("Should delete product idempotently (second delete should not fail)")
    void shouldDeleteIdempotently() {
        ProductDTO created = productService.createProduct(create("ToDelete", 1.0));
        UUID id = created.getId();

        assertThatNoException()
                .as(String.format("First delete should succeed for product ID: %s", id))
                .isThrownBy(() -> productService.deleteProduct(id));

        assertThatNoException()
                .as(String.format("Second delete should also succeed (idempotent delete) for product ID: %s", id))
                .isThrownBy(() -> productService.deleteProduct(id));
    }
}
