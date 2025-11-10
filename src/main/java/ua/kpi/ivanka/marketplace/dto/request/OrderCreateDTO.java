package ua.kpi.ivanka.marketplace.dto.request;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.util.UUID;

@Value
@Builder
@Jacksonized
public class OrderCreateDTO {

    @NotNull(message = "Product ID is required")
    UUID productId;

    @Min(value = 1, message = "Quantity must be at least 1")
    int quantity;

    @NotNull(message = "Total price is required")
    @DecimalMin(value = "0.01", message = "Total price must be greater than 0")
    BigDecimal totalPrice;

    @NotBlank(message = "Status cannot be blank")
    String status;
}
