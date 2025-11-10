package ua.kpi.ivanka.marketplace.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Value
@Builder
@Jacksonized
public class OrderUpdateDTO {

    @Min(value = 1, message = "Quantity must be at least 1")
    int quantity;

    @DecimalMin(value = "0.01", message = "Total price must be greater than 0")
    BigDecimal totalPrice;

    @Size(min = 3, max = 50, message = "Status must be between 3 and 50 characters")
    String status;
}
