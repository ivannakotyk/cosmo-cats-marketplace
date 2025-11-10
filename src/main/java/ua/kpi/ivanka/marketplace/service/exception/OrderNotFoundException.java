package ua.kpi.ivanka.marketplace.service.exception;

import lombok.Getter;
import ua.kpi.ivanka.marketplace.web.exception.ResourceNotFoundException;
import java.util.UUID;

@Getter
public class OrderNotFoundException extends ResourceNotFoundException {

    private final UUID orderId;

    public OrderNotFoundException(UUID orderId) {
        super(String.format("Order with ID %s not found", orderId));
        this.orderId = orderId;
    }
}
