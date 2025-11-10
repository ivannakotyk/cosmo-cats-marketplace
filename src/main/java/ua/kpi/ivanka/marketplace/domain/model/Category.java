package ua.kpi.ivanka.marketplace.domain.model;

import lombok.Data;
import lombok.Builder;
import java.util.UUID;

@Data
@Builder
public class Category {
    private UUID id;
    private String name;
}
