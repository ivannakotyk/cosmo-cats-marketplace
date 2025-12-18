package ua.kpi.ivanka.marketplace.service;

import ua.kpi.ivanka.marketplace.dto.ProductDTO;
import ua.kpi.ivanka.marketplace.dto.request.ProductCreateDTO;
import ua.kpi.ivanka.marketplace.dto.request.ProductUpdateDTO;
import ua.kpi.ivanka.marketplace.repository.projection.PopularProductReport;
import ua.kpi.ivanka.marketplace.repository.projection.ProductSalesReport;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ProductService {
    ProductDTO createProduct(ProductCreateDTO dto);
    List<ProductDTO> listProducts();
    ProductDTO getProduct(UUID id);
    ProductDTO updateProduct(UUID id, ProductUpdateDTO dto);
    void deleteProduct(UUID id);
    Map<String, Object> getRates();
    List<ProductDTO> listProductsByCategory(UUID categoryId);
    List<ProductDTO> searchProducts(String query);
    List<ProductSalesReport> getSalesReport();
    List<PopularProductReport> getPopularProducts();
    List<ProductDTO> listProductsByPriceLessThan(BigDecimal maxPrice);
}