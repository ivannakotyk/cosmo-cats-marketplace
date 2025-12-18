package ua.kpi.ivanka.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.kpi.ivanka.marketplace.repository.entity.CategoryEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long>, NaturalIdRepository<CategoryEntity, UUID> {

    Optional<CategoryEntity> findByName(String name);

    @Query("SELECT c FROM CategoryEntity c WHERE LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<CategoryEntity> searchByDescription(@Param("keyword") String keyword);
}