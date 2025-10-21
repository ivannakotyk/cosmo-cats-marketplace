package ua.kpi.ivanka.marketplace.service.exception;

import ua.kpi.ivanka.marketplace.web.exception.ResourceNotFoundException;
import java.util.UUID;

public class CartNotFoundException extends ResourceNotFoundException {
    private final UUID cartId;

    public CartNotFoundException(UUID cartId) {
        super("Cart with ID " + cartId + " not found");
        this.cartId = cartId;
    }

    public UUID getCartId() {
        return cartId;
    }
}
