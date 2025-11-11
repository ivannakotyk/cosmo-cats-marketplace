package ua.kpi.ivanka.marketplace.config;

import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import ua.kpi.ivanka.marketplace.service.mapper.ProductMapper;

@TestConfiguration
public class MappersTestConfiguration {

    @Bean
    public ProductMapper productMapper() {

        return Mappers.getMapper(ProductMapper.class);
    }
}
