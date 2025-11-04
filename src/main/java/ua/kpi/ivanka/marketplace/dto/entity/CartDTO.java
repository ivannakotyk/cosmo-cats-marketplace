package ua.kpi.ivanka.marketplace.dto.entity;

import lombok.*;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private UUID id;
    private List<UUID> productIds;
    private double totalPrice;
}
