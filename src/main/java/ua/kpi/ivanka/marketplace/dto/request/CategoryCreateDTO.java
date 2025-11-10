package ua.kpi.ivanka.marketplace.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class CategoryCreateDTO {

    @NotBlank(message = "Category name cannot be blank")
    @Size(min = 3, max = 100, message = "Category name must be between 3 and 100 characters")
    String name;
}
