package ua.kpi.ivanka.marketplace.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.kpi.ivanka.marketplace.domain.model.Product;
import ua.kpi.ivanka.marketplace.dto.entity.ProductDTO;
import ua.kpi.ivanka.marketplace.dto.request.ProductCreateDTO;
import ua.kpi.ivanka.marketplace.dto.request.ProductUpdateDTO;
import ua.kpi.ivanka.marketplace.service.exception.ProductNotFoundException;
import ua.kpi.ivanka.marketplace.service.mapper.ProductMapper;
import ua.kpi.ivanka.marketplace.service.ProductService;
import ua.kpi.ivanka.marketplace.util.IdGenerator;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper mapper;
    private final Map<UUID, Product> store = new ConcurrentHashMap<>();

    {
        Product p1 = Product.builder()
                .id(IdGenerator.generate())
                .name("galaxy yarn")
                .price(new BigDecimal("12.50"))
                .description("anti-gravity yarn for zero-g play")
                .categoryId(null)
                .build();
        store.put(p1.getId(), p1);

        Product p2 = Product.builder()
                .id(IdGenerator.generate())
                .name("comet milk")
                .price(new BigDecimal("7.99"))
                .description("ultra-fresh cosmic milk")
                .categoryId(null)
                .build();
        store.put(p2.getId(), p2);

        log.info("Initialized mock product store with {} items", store.size());
    }

    @Override
    public ProductDTO create(ProductCreateDTO dto) {
        Product entity = mapper.toEntity(dto);
        entity.setId(IdGenerator.generate());
        store.put(entity.getId(), entity);
        log.info("Created new product: {} (ID={})", entity.getName(), entity.getId());
        return mapper.toDto(entity);
    }

    @Override
    public List<ProductDTO> list() {
        log.debug("Listing all products (count={})", store.size());
        return store.values().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public ProductDTO get(UUID id) {
        Product entity = store.get(id);
        if (entity == null) {
            log.warn("Product with ID {} not found", id);
            throw new ProductNotFoundException(id);
        }
        log.debug("Retrieved product: {} (ID={})", entity.getName(), id);
        return mapper.toDto(entity);
    }

    @Override
    public ProductDTO update(UUID id, ProductUpdateDTO dto) {
        Product entity = store.get(id);
        if (entity == null) {
            log.warn("Cannot update — product with ID {} not found", id);
            throw new ProductNotFoundException(id);
        }
        mapper.updateEntity(entity, dto);
        log.info("Updated product: {} (ID={})", entity.getName(), id);
        return mapper.toDto(entity);
    }

    @Override
    public void delete(UUID id) {
        if (store.remove(id) != null) {
            log.info("Deleted product with ID {}", id);
        } else {
            log.debug("Product with ID {} already missing (delete is idempotent)", id);
        }
    }
}
