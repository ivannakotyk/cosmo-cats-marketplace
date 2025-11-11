package ua.kpi.ivanka.marketplace.service.exception;

import lombok.Getter;
import ua.kpi.ivanka.marketplace.web.exception.ResourceNotFoundException;
import java.util.UUID;

@Getter
public class CategoryNotFoundException extends ResourceNotFoundException {

    private final UUID categoryId;

    public CategoryNotFoundException(UUID categoryId) {
        super(String.format("Category with ID %s not found", categoryId));
        this.categoryId = categoryId;
    }
}
