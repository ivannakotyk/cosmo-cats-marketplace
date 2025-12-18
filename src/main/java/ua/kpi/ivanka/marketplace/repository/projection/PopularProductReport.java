package ua.kpi.ivanka.marketplace.repository.projection;

public record PopularProductReport(
        String productName,
        Long orderCount,
        String categoryName
) {
}