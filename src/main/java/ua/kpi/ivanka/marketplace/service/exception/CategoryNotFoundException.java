package ua.kpi.ivanka.marketplace.service.exception;

import ua.kpi.ivanka.marketplace.web.exception.ResourceNotFoundException;
import java.util.UUID;

public class CategoryNotFoundException extends ResourceNotFoundException {
    private final UUID categoryId;

    public CategoryNotFoundException(UUID categoryId) {
        super("Category with ID " + categoryId + " not found");
        this.categoryId = categoryId;
    }

    public UUID getCategoryId() {
        return categoryId;
    }
}
