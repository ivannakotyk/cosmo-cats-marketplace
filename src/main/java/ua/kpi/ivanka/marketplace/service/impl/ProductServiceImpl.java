package ua.kpi.ivanka.marketplace.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kpi.ivanka.marketplace.client.RatesClient;
import ua.kpi.ivanka.marketplace.dto.ProductDTO;
import ua.kpi.ivanka.marketplace.dto.request.ProductCreateDTO;
import ua.kpi.ivanka.marketplace.dto.request.ProductUpdateDTO;
import ua.kpi.ivanka.marketplace.repository.CategoryRepository;
import ua.kpi.ivanka.marketplace.repository.ProductRepository;
import ua.kpi.ivanka.marketplace.repository.entity.CategoryEntity;
import ua.kpi.ivanka.marketplace.repository.entity.ProductEntity;
import ua.kpi.ivanka.marketplace.repository.exception.CosmicPersistenceException;
import ua.kpi.ivanka.marketplace.repository.projection.PopularProductReport;
import ua.kpi.ivanka.marketplace.repository.projection.ProductSalesReport;
import ua.kpi.ivanka.marketplace.service.ProductService;
import ua.kpi.ivanka.marketplace.service.exception.CategoryNotFoundException;
import ua.kpi.ivanka.marketplace.service.exception.ProductNotFoundException;
import ua.kpi.ivanka.marketplace.service.mapper.ProductMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper mapper;
    private final RatesClient ratesClient;

    @Override
    @Transactional
    public ProductDTO createProduct(ProductCreateDTO dto) {
        log.info("Attempting to create product with name: {}", dto.getName());
        try {
            CategoryEntity category = null;
            if (dto.getCategoryId() != null) {
                category = categoryRepository.findByNaturalId(dto.getCategoryId())
                        .orElseThrow(() -> {
                            log.warn("Category with ID {} not found during product creation", dto.getCategoryId());
                            return new CategoryNotFoundException(dto.getCategoryId());
                        });
            }

            ProductEntity entity = mapper.toEntity(dto);
            entity.setCategory(category);

            ProductEntity savedEntity = productRepository.save(entity);
            log.info("Product created successfully. Public ID: {}", savedEntity.getPublicId());

            return mapper.toProductDTO(savedEntity);

        } catch (CategoryNotFoundException e) {
            throw e;
        } catch (Exception ex) {
            log.error("Exception occurred while saving product: {}", ex.getMessage());
            throw new CosmicPersistenceException("Failed to create product", ex);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> listProducts() {
        log.debug("Fetching all products");
        return productRepository.findAll().stream()
                .map(mapper::toProductDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO getProduct(UUID id) {
        log.debug("Fetching product by ID: {}", id);
        return productRepository.findByNaturalId(id)
                .map(mapper::toProductDTO)
                .orElseThrow(() -> {
                    log.warn("Product with ID {} not found", id);
                    return new ProductNotFoundException(id);
                });
    }

    @Override
    @Transactional
    public ProductDTO updateProduct(UUID id, ProductUpdateDTO dto) {
        log.info("Attempting to update product with ID: {}", id);
        try {
            ProductEntity entity = productRepository.findByNaturalId(id)
                    .orElseThrow(() -> new ProductNotFoundException(id));

            if (dto.getCategoryId() != null) {
                CategoryEntity category = categoryRepository.findByNaturalId(dto.getCategoryId())
                        .orElseThrow(() -> new CategoryNotFoundException(dto.getCategoryId()));
                entity.setCategory(category);
            }

            mapper.updateProduct(entity, dto);

            ProductEntity updatedEntity = productRepository.save(entity);
            log.info("Product updated successfully: {}", id);

            return mapper.toProductDTO(updatedEntity);

        } catch (ProductNotFoundException | CategoryNotFoundException e) {
            throw e;
        } catch (Exception ex) {
            log.error("Exception occurred while updating product: {}", ex.getMessage());
            throw new CosmicPersistenceException("Failed to update product", ex);
        }
    }

    @Override
    @Transactional
    public void deleteProduct(UUID id) {
        log.info("Attempting to delete product with ID: {}", id);
        try {
            if (productRepository.findByNaturalId(id).isEmpty()) {
                log.warn("Product {} not found, skipping delete", id);
                return;
            }

            productRepository.deleteByNaturalId(id);
            log.info("Product deleted successfully: {}", id);

        } catch (Exception ex) {
            log.error("Exception occurred while deleting product: {}", ex.getMessage());
            throw new CosmicPersistenceException("Failed to delete product", ex);
        }
    }

    @Override
    public Map<String, Object> getRates() {
        return ratesClient.getRates();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> listProductsByCategory(UUID categoryId) {
        log.info("Fetching products for category: {}", categoryId);
        return mapper.toProductDTOs(productRepository.findByCategory_PublicId(categoryId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> searchProducts(String query) {
        log.info("Searching products by query: {}", query);
        if (query == null || query.isBlank()) {
            return listProducts();
        }
        return mapper.toProductDTOs(productRepository.searchProducts(query));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductSalesReport> getSalesReport() {
        log.info("Generating sales report");
        List<ProductSalesReport> reports = productRepository.getProductSalesReport();
        reports.forEach(report -> log.info(report.getSalesSummary()));

        return reports;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PopularProductReport> getPopularProducts() {
        log.info("Generating popular products report");
        return productRepository.getPopularProducts();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> listProductsByPriceLessThan(BigDecimal maxPrice) {
        log.info("Fetching products cheaper than: {}", maxPrice);
        return productRepository.findByPriceLessThan(maxPrice).stream()
                .map(mapper::toProductDTO)
                .toList();
    }
}
