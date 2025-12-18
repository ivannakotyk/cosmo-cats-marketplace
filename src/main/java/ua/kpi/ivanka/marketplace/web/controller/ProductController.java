package ua.kpi.ivanka.marketplace.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ua.kpi.ivanka.marketplace.dto.ProductDTO;
import ua.kpi.ivanka.marketplace.dto.request.ProductCreateDTO;
import ua.kpi.ivanka.marketplace.dto.request.ProductUpdateDTO;
import ua.kpi.ivanka.marketplace.client.RatesClient;
import ua.kpi.ivanka.marketplace.service.ProductService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;
    private final RatesClient ratesClient;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> listProducts() {
        List<ProductDTO> products = service.listProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable UUID id) {
        ProductDTO product = service.getProduct(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductCreateDTO dto) {
        ProductDTO createdProduct = service.createProduct(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable UUID id,
                                                    @Valid @RequestBody ProductUpdateDTO dto) {
        ProductDTO updatedProduct = service.updateProduct(id, dto);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/rates")
    public ResponseEntity<Map<String, Object>> getCosmicRates() {
        Map<String, Object> rates = service.getRates();
        return ResponseEntity.ok(rates);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getByCategory(@PathVariable UUID categoryId) {
        return ResponseEntity.ok(service.listProductsByCategory(categoryId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> search(@RequestParam("query") String query) {
        return ResponseEntity.ok(service.searchProducts(query));
    }

    @GetMapping("/reports/sales")
    public ResponseEntity<List<ua.kpi.ivanka.marketplace.repository.projection.ProductSalesReport>> getSalesReport() {
        return ResponseEntity.ok(service.getSalesReport());
    }

    @GetMapping("/reports/popular")
    public ResponseEntity<List<ua.kpi.ivanka.marketplace.repository.projection.PopularProductReport>> getPopularReport() {
        return ResponseEntity.ok(service.getPopularProducts());
    }

    @GetMapping("/search/price")
    public ResponseEntity<List<ProductDTO>> searchByMaxPrice(@RequestParam("max") java.math.BigDecimal maxPrice) {
        return ResponseEntity.ok(service.listProductsByPriceLessThan(maxPrice));
    }
}
