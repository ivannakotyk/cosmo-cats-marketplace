package ua.kpi.ivanka.marketplace.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {

    private String apiKeyHeader = "X-API-KEY";
    private String apiKey = "cosmo-cats-api-key";
    private Jwt jwt = new Jwt();

    @Getter
    @Setter
    public static class Jwt {
        private String secret = "super-secret-key-that-must-be-very-long-for-hs256";
        private String jwsAlgorithm = "HS256";
        private String rolesClaim = "roles";
    }
}