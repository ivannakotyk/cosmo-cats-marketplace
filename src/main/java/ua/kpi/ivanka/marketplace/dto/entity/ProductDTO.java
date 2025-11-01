package ua.kpi.ivanka.marketplace.dto.entity;

import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class ProductDTO {
    private UUID id;
    private String name;
    private BigDecimal price;
    private String description;
    private UUID categoryId;
}
