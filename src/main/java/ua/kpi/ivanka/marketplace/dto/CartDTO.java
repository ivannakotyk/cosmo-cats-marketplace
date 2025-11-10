package ua.kpi.ivanka.marketplace.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import java.util.List;
import java.util.UUID;

@Value
@Builder
@Jacksonized
public class CartDTO {
    UUID id;
    List<UUID> productIds;
    double totalPrice;
}
