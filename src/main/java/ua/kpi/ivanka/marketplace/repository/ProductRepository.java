package ua.kpi.ivanka.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.kpi.ivanka.marketplace.repository.entity.ProductEntity;
import ua.kpi.ivanka.marketplace.repository.projection.PopularProductReport;
import ua.kpi.ivanka.marketplace.repository.projection.ProductSalesReport;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long>, NaturalIdRepository<ProductEntity, UUID> {

    List<ProductEntity> findByCategory_PublicId(UUID categoryPublicId);

    List<ProductEntity> findByPriceLessThan(BigDecimal price);

    @Query("""
        SELECT new ua.kpi.ivanka.marketplace.repository.projection.ProductSalesReport(
            p.name,
            SUM(oi.quantity),
            SUM(oi.quantity * oi.priceAtOrder)
        )
        FROM OrderItemEntity oi
        JOIN oi.product p
        GROUP BY p.name
        ORDER BY SUM(oi.quantity * oi.priceAtOrder) DESC
    """)
    List<ProductSalesReport> getProductSalesReport();

    @Query("""
        SELECT new ua.kpi.ivanka.marketplace.repository.projection.PopularProductReport(
            p.name,
            COUNT(oi),
            p.category.name
        )
        FROM ProductEntity p
        LEFT JOIN p.orderItems oi
        GROUP BY p.name, p.category.name
        HAVING COUNT(oi) > 0
        ORDER BY COUNT(oi) DESC
    """)
    List<PopularProductReport> getPopularProducts();

    @Query("""
        SELECT p FROM ProductEntity p 
        WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) 
           OR LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%'))
    """)
    List<ProductEntity> searchProducts(@Param("query") String query);
}