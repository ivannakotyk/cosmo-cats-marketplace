package ua.kpi.ivanka.marketplace.featuretoggle.annotation;

import ua.kpi.ivanka.marketplace.featuretoggle.FeatureToggles;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FeatureToggle {
    FeatureToggles value();
}

