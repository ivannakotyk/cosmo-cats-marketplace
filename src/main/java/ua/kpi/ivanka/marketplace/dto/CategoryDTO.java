package ua.kpi.ivanka.marketplace.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized; // Зручно для JSON десеріалізації

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
public class CategoryDTO {

    private UUID id;

    @NotBlank(message = "Category name is required")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 chars")
    private String name;

    @Size(max = 500, message = "Description too long")
    private String description;
}