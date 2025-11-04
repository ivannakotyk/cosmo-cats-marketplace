package ua.kpi.ivanka.marketplace.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import ua.kpi.ivanka.marketplace.validation.CosmicWordCheck;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCreateDTO {

    @NotNull(message = "Product name cannot be null")
    @Size(min = 3, max = 255, message = "Product name must be between 3 and 255 characters")
    @CosmicWordCheck
    private String name;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal price;

    @Size(max = 1024, message = "Description cannot exceed 1024 characters")
    private String description;

    private UUID categoryId;
}
