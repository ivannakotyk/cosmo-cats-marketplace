package ua.kpi.ivanka.marketplace.service.exception;

import ua.kpi.ivanka.marketplace.web.exception.ResourceNotFoundException;

public class CategoryByNameNotFoundException extends ResourceNotFoundException {

    public CategoryByNameNotFoundException(String name) {
        super(String.format("Category with name '%s' not found", name));
    }
}