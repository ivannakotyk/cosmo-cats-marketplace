package ua.kpi.ivanka.marketplace.web;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ua.kpi.ivanka.marketplace.featuretoggle.FeatureToggleExtension;
import ua.kpi.ivanka.marketplace.featuretoggle.FeatureToggles;
import ua.kpi.ivanka.marketplace.featuretoggle.annotation.DisabledFeatureToggle;
import ua.kpi.ivanka.marketplace.featuretoggle.annotation.EnabledFeatureToggle;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(FeatureToggleExtension.class)
@DisplayName("Cosmo Controller Integration Tests")
class CosmoControllerIT {

    @Autowired
    private MockMvc mockMvc;
    private static final String CATS_URL = "/api/v1/cosmo/cats";
    private static final String PRODUCTS_URL = "/api/v1/cosmo/products";


    @Test
    @SneakyThrows
    @DisplayName("Should return 404 when COSMO_CATS feature is disabled")
    @DisabledFeatureToggle(FeatureToggles.COSMO_CATS)
    void shouldReturn404WhenCatsDisabled() {
        mockMvc.perform(get(CATS_URL))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    @DisplayName("Should return 200 OK when COSMO_CATS feature is enabled")
    @EnabledFeatureToggle(FeatureToggles.COSMO_CATS)
    void shouldReturn200WhenCatsEnabled() {
        mockMvc.perform(get(CATS_URL))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @DisplayName("Should return 404 when KITTY_PRODUCTS feature is disabled")
    @DisabledFeatureToggle(FeatureToggles.KITTY_PRODUCTS)
    void shouldReturn404WhenProductsDisabled() {
        mockMvc.perform(get(PRODUCTS_URL))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    @DisplayName("Should return 200 OK when KITTY_PRODUCTS feature is enabled")
    @EnabledFeatureToggle(FeatureToggles.KITTY_PRODUCTS)
    void shouldReturn200WhenProductsEnabled() {
        mockMvc.perform(get(PRODUCTS_URL))
                .andExpect(status().isOk());
    }
}