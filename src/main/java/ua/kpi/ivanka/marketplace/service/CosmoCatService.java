package ua.kpi.ivanka.marketplace.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.kpi.ivanka.marketplace.featuretoggle.FeatureToggles;
import ua.kpi.ivanka.marketplace.featuretoggle.annotation.FeatureToggle;

import java.util.List;

@Slf4j
@Service
public class CosmoCatService {

    @FeatureToggle(FeatureToggles.COSMO_CATS)
    public List<String> getCosmoCats() {
        log.info("Feature '{}' enabled → Fetching Cosmo Cats",
                FeatureToggles.COSMO_CATS.getPropertyKey());
        return List.of(
                "Luna the Voyager",
                "Cosmo the Navigator",
                "Nebula the Dreamer",
                "Starburst the Adventurer"
        );
    }

    @FeatureToggle(FeatureToggles.KITTY_PRODUCTS)
    public List<String> getKittyProducts() {
        log.info("Feature '{}' enabled → Fetching Kitty Products",
                FeatureToggles.KITTY_PRODUCTS.getPropertyKey());
        return List.of(
                "Astro Tuna Pack",
                "Cosmic Laser Pointer",
                "Zero-Gravity Catnip",
                "Stellar Milk Snack"
        );
    }
}
