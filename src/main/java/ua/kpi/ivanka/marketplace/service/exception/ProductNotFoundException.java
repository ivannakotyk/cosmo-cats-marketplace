package ua.kpi.ivanka.marketplace.service.exception;

import lombok.Getter;
import ua.kpi.ivanka.marketplace.web.exception.ResourceNotFoundException;
import java.util.UUID;

@Getter
public class ProductNotFoundException extends ResourceNotFoundException {

    private final UUID productId;

    public ProductNotFoundException(UUID productId) {
        super(String.format("Product with ID %s not found", productId));
        this.productId = productId;
    }
}
