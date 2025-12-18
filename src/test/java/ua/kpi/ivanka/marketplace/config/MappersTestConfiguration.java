package ua.kpi.ivanka.marketplace.config;

import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import ua.kpi.ivanka.marketplace.service.mapper.CategoryMapper;
import ua.kpi.ivanka.marketplace.service.mapper.OrderMapper;
import ua.kpi.ivanka.marketplace.service.mapper.ProductMapper;

@TestConfiguration
public class MappersTestConfiguration {

    @Bean
    public ProductMapper productMapper() {
        return Mappers.getMapper(ProductMapper.class);
    }

    @Bean
    public CategoryMapper categoryMapper() {
        return Mappers.getMapper(CategoryMapper.class);
    }

    @Bean
    public OrderMapper orderMapper() {
        return Mappers.getMapper(OrderMapper.class);
    }
}