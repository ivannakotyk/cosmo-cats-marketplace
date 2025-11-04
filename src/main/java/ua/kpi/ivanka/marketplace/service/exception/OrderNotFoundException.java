package ua.kpi.ivanka.marketplace.service.exception;

import ua.kpi.ivanka.marketplace.web.exception.ResourceNotFoundException;
import java.util.UUID;

public class OrderNotFoundException extends ResourceNotFoundException {
    private final UUID orderId;

    public OrderNotFoundException(UUID orderId) {
        super("Order with ID " + orderId + " not found");
        this.orderId = orderId;
    }

    public UUID getOrderId() {
        return orderId;
    }
}

