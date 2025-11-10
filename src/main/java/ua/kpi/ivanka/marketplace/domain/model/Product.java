package ua.kpi.ivanka.marketplace.domain.model;

import lombok.Data;
import lombok.Builder;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class Product {
    private UUID id;
    private String name;
    private BigDecimal price;
    private String description;
    private UUID categoryId;
}
