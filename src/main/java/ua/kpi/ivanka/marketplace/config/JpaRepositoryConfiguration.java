package ua.kpi.ivanka.marketplace.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ua.kpi.ivanka.marketplace.repository.impl.NaturalIdRepositoryImpl;

@Configuration
@EnableJpaRepositories(
        basePackages = "ua.kpi.ivanka.marketplace.repository",
        repositoryBaseClass = NaturalIdRepositoryImpl.class
)
public class JpaRepositoryConfiguration {}