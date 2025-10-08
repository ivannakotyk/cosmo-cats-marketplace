package ua.kpi.ivanka.marketplace.domain.model;

import lombok.*;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Order {
    private UUID id;
    private Instant createdAt;
    private List<UUID> productIds;
}
