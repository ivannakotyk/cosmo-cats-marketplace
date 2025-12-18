package ua.kpi.ivanka.marketplace.repository.projection;

import java.math.BigDecimal;

public record ProductSalesReport(
        String productName,
        Long totalQuantity,
        BigDecimal totalRevenue
) {
    public String getSalesSummary() {
        return String.format("Product: %s, Sold: %d, Revenue: %s",
                productName(), totalQuantity(), totalRevenue());
    }
}