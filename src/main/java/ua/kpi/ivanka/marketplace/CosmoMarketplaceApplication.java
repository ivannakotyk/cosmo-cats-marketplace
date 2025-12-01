package ua.kpi.ivanka.marketplace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class CosmoMarketplaceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CosmoMarketplaceApplication.class, args);
    }
}
