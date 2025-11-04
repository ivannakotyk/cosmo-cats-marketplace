package ua.kpi.ivanka.marketplace.domain.model;

import lombok.*;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {
    private UUID id;
    private List<UUID> productIds;
    private double totalPrice;
}
