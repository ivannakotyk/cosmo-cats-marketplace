package ua.kpi.ivanka.marketplace.dto;

import lombok.*;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    UUID id;
    String name;
}
