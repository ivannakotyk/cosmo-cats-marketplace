package ua.kpi.ivanka.marketplace.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    UUID id;
    UUID productId;
    int quantity;
    BigDecimal totalPrice;
    String status;
    LocalDateTime createdAt;
}
