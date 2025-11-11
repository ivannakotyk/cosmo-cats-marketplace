package ua.kpi.ivanka.marketplace.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    UUID id;
    UUID productId;
    int quantity;
    BigDecimal totalPrice;
    String status;
    LocalDateTime createdAt;
}
