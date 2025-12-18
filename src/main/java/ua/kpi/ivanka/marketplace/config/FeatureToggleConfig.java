package ua.kpi.ivanka.marketplace.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "application.feature")
@Data
@NoArgsConstructor
public class FeatureToggleConfig {
    private Map<String, Boolean> toggles;
}
