package ua.kpi.ivanka.marketplace.featuretoggle;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.kpi.ivanka.marketplace.featuretoggle.FeatureToggleService;
import ua.kpi.ivanka.marketplace.featuretoggle.annotation.DisabledFeatureToggle;
import ua.kpi.ivanka.marketplace.featuretoggle.annotation.EnabledFeatureToggle;

public class FeatureToggleExtension implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) {
        context.getTestMethod().ifPresent(method -> {
            FeatureToggleService featureToggleService = getFeatureToggleService(context);

            if (method.isAnnotationPresent(EnabledFeatureToggle.class)) {
                EnabledFeatureToggle annotation = method.getAnnotation(EnabledFeatureToggle.class);
                // Використовуємо getPropertyKey(), бо так називається поле в твоєму Enum
                featureToggleService.enable(annotation.value().getPropertyKey());
            } else if (method.isAnnotationPresent(DisabledFeatureToggle.class)) {
                DisabledFeatureToggle annotation = method.getAnnotation(DisabledFeatureToggle.class);
                featureToggleService.disable(annotation.value().getPropertyKey());
            }
        });
    }

    @Override
    public void afterEach(ExtensionContext context) {
        context.getTestMethod().ifPresent(method -> {
            String propertyKey = null;

            if (method.isAnnotationPresent(EnabledFeatureToggle.class)) {
                propertyKey = method.getAnnotation(EnabledFeatureToggle.class).value().getPropertyKey();
            } else if (method.isAnnotationPresent(DisabledFeatureToggle.class)) {
                propertyKey = method.getAnnotation(DisabledFeatureToggle.class).value().getPropertyKey();
            }

            // Якщо ми щось змінювали, треба повернути стан як було в application.yaml
            if (propertyKey != null) {
                FeatureToggleService featureToggleService = getFeatureToggleService(context);
                if (getFeatureOriginalValue(context, propertyKey)) {
                    featureToggleService.enable(propertyKey);
                } else {
                    featureToggleService.disable(propertyKey);
                }
            }
        });
    }

    private boolean getFeatureOriginalValue(ExtensionContext context, String propertyKey) {
        Environment environment = SpringExtension.getApplicationContext(context).getEnvironment();
        // Перевіряємо оригінальне значення з конфігу
        return environment.getProperty("application.feature.toggles." + propertyKey, Boolean.class, Boolean.FALSE);
    }

    private FeatureToggleService getFeatureToggleService(ExtensionContext context) {
        return SpringExtension.getApplicationContext(context).getBean(FeatureToggleService.class);
    }
}