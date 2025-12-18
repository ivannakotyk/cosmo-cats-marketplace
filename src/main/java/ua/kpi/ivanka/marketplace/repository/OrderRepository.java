package ua.kpi.ivanka.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.kpi.ivanka.marketplace.repository.entity.OrderEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long>, NaturalIdRepository<OrderEntity, UUID> {
    List<OrderEntity> findByStatus(String status);
    List<OrderEntity> findByCreatedAtAfter(LocalDateTime date);

    @Query("SELECT o FROM OrderEntity o WHERE o.totalPrice > :minAmount ORDER BY o.totalPrice DESC")
    List<OrderEntity> findHighValueOrders(@Param("minAmount") BigDecimal minAmount);
}