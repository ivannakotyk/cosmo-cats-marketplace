package ua.kpi.ivanka.marketplace.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import java.math.BigDecimal;
import java.util.UUID;

@Value
@Builder
@Jacksonized
public class ProductDTO {
    UUID id;
    String name;
    BigDecimal price;
    String description;
    UUID categoryId;
}
