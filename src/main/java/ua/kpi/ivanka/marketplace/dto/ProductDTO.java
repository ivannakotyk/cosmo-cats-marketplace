package ua.kpi.ivanka.marketplace.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    UUID id;
    String name;
    BigDecimal price;
    String description;
    UUID categoryId;
}
