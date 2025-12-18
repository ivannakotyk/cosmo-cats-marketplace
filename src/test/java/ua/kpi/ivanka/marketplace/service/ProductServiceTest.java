package ua.kpi.ivanka.marketplace.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import ua.kpi.ivanka.marketplace.client.RatesClient;
import ua.kpi.ivanka.marketplace.config.MappersTestConfiguration;
import ua.kpi.ivanka.marketplace.dto.ProductDTO;
import ua.kpi.ivanka.marketplace.dto.request.ProductCreateDTO;
import ua.kpi.ivanka.marketplace.dto.request.ProductUpdateDTO;
import ua.kpi.ivanka.marketplace.repository.CategoryRepository;
import ua.kpi.ivanka.marketplace.repository.ProductRepository;
import ua.kpi.ivanka.marketplace.repository.entity.CategoryEntity;
import ua.kpi.ivanka.marketplace.repository.entity.ProductEntity;
import ua.kpi.ivanka.marketplace.service.exception.CategoryNotFoundException;
import ua.kpi.ivanka.marketplace.service.impl.ProductServiceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {ProductServiceImpl.class})
@Import(MappersTestConfiguration.class)
@DisplayName("Product Service Unit Tests")
class ProductServiceTest {

    @MockBean private ProductRepository productRepository;
    @MockBean private CategoryRepository categoryRepository;
    @MockBean private RatesClient ratesClient;

    @Autowired private ProductService productService;

    private static final UUID CAT_ID = UUID.randomUUID();
    private static final UUID PROD_ID = UUID.randomUUID();

    @Test
    @DisplayName("Should create product successfully")
    void shouldCreateProduct() {
        ProductCreateDTO request = ProductCreateDTO.builder().name("Cosmic Toy").price(BigDecimal.TEN).categoryId(CAT_ID).build();
        when(categoryRepository.findByNaturalId(CAT_ID)).thenReturn(Optional.of(CategoryEntity.builder().publicId(CAT_ID).build()));
        when(productRepository.save(any(ProductEntity.class))).thenAnswer(inv -> {
            ProductEntity p = inv.getArgument(0);
            p.setPublicId(PROD_ID);
            return p;
        });

        ProductDTO result = productService.createProduct(request);
        assertThat(result.getName()).isEqualTo("Cosmic Toy");
    }

    @Test
    @DisplayName("Should throw exception when category not found")
    void shouldThrowIfCategoryNotFound() {
        ProductCreateDTO request = ProductCreateDTO.builder().categoryId(CAT_ID).build();
        when(categoryRepository.findByNaturalId(CAT_ID)).thenReturn(Optional.empty());
        assertThrows(CategoryNotFoundException.class, () -> productService.createProduct(request));
    }

    @Test
    @DisplayName("Should update product")
    void shouldUpdateProduct() {
        ProductUpdateDTO updateRequest = ProductUpdateDTO.builder().name("Updated Name").price(BigDecimal.ONE).build();
        ProductEntity existingProduct = ProductEntity.builder().publicId(PROD_ID).name("Old Name").category(CategoryEntity.builder().publicId(CAT_ID).build()).build();
        when(productRepository.findByNaturalId(PROD_ID)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        ProductDTO result = productService.updateProduct(PROD_ID, updateRequest);
        assertThat(result.getName()).isEqualTo("Updated Name");
    }

    @Test
    @DisplayName("Should delete product")
    void shouldDeleteProduct() {
        ProductEntity existingProduct = ProductEntity.builder().publicId(PROD_ID).build();
        when(productRepository.findByNaturalId(PROD_ID)).thenReturn(Optional.of(existingProduct));
        productService.deleteProduct(PROD_ID);
        verify(productRepository).deleteByNaturalId(PROD_ID);
    }

    @Test
    @DisplayName("Should list all products")
    void shouldListProducts() {
        when(productRepository.findAll()).thenReturn(List.of(ProductEntity.builder().name("P1").build()));
        List<ProductDTO> list = productService.listProducts();
        assertThat(list).hasSize(1);
    }

    @Test
    @DisplayName("Should get product by ID")
    void shouldGetProduct() {
        when(productRepository.findByNaturalId(PROD_ID)).thenReturn(Optional.of(ProductEntity.builder().name("P1").build()));
        ProductDTO dto = productService.getProduct(PROD_ID);
        assertThat(dto.getName()).isEqualTo("P1");
    }
}