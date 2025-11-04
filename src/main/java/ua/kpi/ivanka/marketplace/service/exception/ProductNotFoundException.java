package ua.kpi.ivanka.marketplace.service.exception;

import ua.kpi.ivanka.marketplace.web.exception.ResourceNotFoundException;
import java.util.UUID;

public class ProductNotFoundException extends ResourceNotFoundException {
    private final UUID productId;

    public ProductNotFoundException(UUID productId) {
        super("Product with ID " + productId + " not found");
        this.productId = productId;
    }

    public UUID getProductId() {
        return productId;
    }
}
