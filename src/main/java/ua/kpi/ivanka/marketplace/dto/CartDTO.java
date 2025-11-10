package ua.kpi.ivanka.marketplace.dto;

import lombok.*;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    UUID id;
    List<UUID> productIds;
    double totalPrice;
}
