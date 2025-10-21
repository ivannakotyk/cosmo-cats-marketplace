package ua.kpi.ivanka.marketplace.domain.model;

import lombok.*;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Category {
    private UUID id;
    private String name;
}
