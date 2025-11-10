package ua.kpi.ivanka.marketplace.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Value
@Builder
@Jacksonized
public class OrderDTO {
    UUID id;
    UUID productId;
    int quantity;
    BigDecimal totalPrice;
    String status;
    LocalDateTime createdAt;
}
