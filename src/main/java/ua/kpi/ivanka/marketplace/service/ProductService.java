package ua.kpi.ivanka.marketplace.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.kpi.ivanka.marketplace.domain.model.Product;
import ua.kpi.ivanka.marketplace.dto.entity.ProductDTO;
import ua.kpi.ivanka.marketplace.dto.request.ProductCreateDTO;
import ua.kpi.ivanka.marketplace.dto.request.ProductUpdateDTO;
import ua.kpi.ivanka.marketplace.exception.ResourceNotFoundException;
import ua.kpi.ivanka.marketplace.mapper.ProductMapper;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper mapper;
    private final Map<UUID, Product> store = new ConcurrentHashMap<>();

    {
        Product p1 = Product.builder()
                .id(UUID.randomUUID())
                .name("galaxy yarn")
                .price(new BigDecimal("12.50"))
                .description("anti-gravity yarn for zero-g play")
                .categoryId(null)
                .build();
        store.put(p1.getId(), p1);

        Product p2 = Product.builder()
                .id(UUID.randomUUID())
                .name("comet milk")
                .price(new BigDecimal("7.99"))
                .description("ultra-fresh cosmic milk")
                .categoryId(null)
                .build();
        store.put(p2.getId(), p2);

        log.info("Initialized mock product store with {} items", store.size());
    }

    public ProductDTO create(ProductCreateDTO dto) {
        Product e = mapper.toEntity(dto);
        e.setId(UUID.randomUUID());
        store.put(e.getId(), e);
        log.info("Created new product: {} (ID={})", e.getName(), e.getId());
        return mapper.toDto(e);
    }

    public List<ProductDTO> list() {
        log.debug("Listing all products (count={})", store.size());
        return store.values().stream().map(mapper::toDto).toList();
    }

    public ProductDTO get(UUID id) {
        Product e = store.get(id);
        if (e == null) {
            log.warn("Product with ID {} not found", id);
            throw new ResourceNotFoundException("Product %s not found".formatted(id));
        }
        log.debug("Retrieved product: {} (ID={})", e.getName(), id);
        return mapper.toDto(e);
    }

    public ProductDTO update(UUID id, ProductUpdateDTO dto) {
        Product e = store.get(id);
        if (e == null) {
            log.warn("Cannot update — product with ID {} not found", id);
            throw new ResourceNotFoundException("Product %s not found".formatted(id));
        }
        mapper.updateEntity(e, dto);
        log.info("Updated product: {} (ID={})", e.getName(), id);
        return mapper.toDto(e);
    }

    public void delete(UUID id) {
        if (!store.containsKey(id)) {
            log.warn("Cannot delete — product with ID {} not found", id);
            throw new ResourceNotFoundException("Product %s not found".formatted(id));
        }
        store.remove(id);
        log.info("Deleted product with ID {}", id);
    }
}
