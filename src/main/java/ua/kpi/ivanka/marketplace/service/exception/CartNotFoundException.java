package ua.kpi.ivanka.marketplace.service.exception;

import lombok.Getter;
import ua.kpi.ivanka.marketplace.web.exception.ResourceNotFoundException;
import java.util.UUID;

@Getter
public class CartNotFoundException extends ResourceNotFoundException {

    private final UUID cartId;

    public CartNotFoundException(UUID cartId) {
        super(String.format("Cart with ID %s not found", cartId));
        this.cartId = cartId;
    }

}
