package ua.kpi.ivanka.marketplace.featuretoggle;

import org.springframework.stereotype.Service;
import ua.kpi.ivanka.marketplace.config.FeatureToggleConfig;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class FeatureToggleService {

    private final ConcurrentHashMap<String, Boolean> featureToggles;

    public FeatureToggleService(FeatureToggleConfig config) {
        this.featureToggles = new ConcurrentHashMap<>(config.getToggles());
    }

    public boolean isEnabled(String propertyKey) {
        return featureToggles.getOrDefault(propertyKey, false);
    }

    public void enable(String propertyKey) {
        featureToggles.put(propertyKey, true);
    }

    public void disable(String propertyKey) {
        featureToggles.put(propertyKey, false);
    }
}
