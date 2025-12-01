package ua.kpi.ivanka.marketplace.featuretoggle;

import lombok.Getter;

@Getter
public enum FeatureToggles {

    COSMO_CATS("cosmo-cats"),
    KITTY_PRODUCTS("kitty-products");

    private final String propertyKey;

    FeatureToggles(String propertyKey) {
        this.propertyKey = propertyKey;
    }
}
