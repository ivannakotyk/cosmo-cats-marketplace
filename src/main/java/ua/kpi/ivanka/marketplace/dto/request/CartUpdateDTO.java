package ua.kpi.ivanka.marketplace.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartUpdateDTO {

    @NotEmpty(message = "Cart must contain at least one product")
    private List<UUID> productIds;
}
