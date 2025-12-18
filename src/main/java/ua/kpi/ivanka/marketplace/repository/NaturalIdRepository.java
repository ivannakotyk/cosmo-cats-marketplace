package ua.kpi.ivanka.marketplace.repository;

import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.Optional;

@NoRepositoryBean
public interface NaturalIdRepository<T, NID extends Serializable> {

    Optional<T> findByNaturalId(NID naturalId);

    void deleteByNaturalId(NID naturalId);
}