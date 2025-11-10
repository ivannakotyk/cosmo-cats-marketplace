package ua.kpi.ivanka.marketplace.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.UUID;

@Value
@Builder
@Jacksonized
public class CartUpdateDTO {

    @NotEmpty(message = "Cart must contain at least one product")
    List<UUID> productIds;
}
