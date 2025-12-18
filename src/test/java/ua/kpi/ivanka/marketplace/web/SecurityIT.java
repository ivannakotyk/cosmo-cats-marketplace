package ua.kpi.ivanka.marketplace.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ua.kpi.ivanka.marketplace.AbstractIT;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles(value = "test", inheritProfiles = false)
@TestPropertySource(properties = {
        "security.api-key-header=X-API-KEY",
        "security.api-key=cosmo-cats-api-key"
})
class SecurityIT extends AbstractIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should return 401 and JSON error when requesting without token")
    void shouldReturn401WithoutToken() throws Exception {
        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value(containsString("Unauthorized")));
    }

    @Test
    @DisplayName("Should return 401 with explicit error body when API key is invalid")
    void shouldReturn401WithInvalidApiKey() throws Exception {
        mockMvc.perform(get("/api/v1/products")
                        .header("X-API-KEY", "invalid-api-key"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Invalid API key provided"));
    }

    @Test
    @DisplayName("Should return 200 when requesting with valid API Key")
    void shouldReturn200WithValidApiKey() throws Exception {
        mockMvc.perform(get("/api/v1/products")
                        .header("X-API-KEY", "cosmo-cats-api-key"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should return 200 when requesting with valid JWT")
    void shouldReturn200WithJwt() throws Exception {
        mockMvc.perform(get("/api/v1/products")
                        .with(jwt().authorities(
                                new SimpleGrantedAuthority("ROLE_USER")
                        )))
                .andExpect(status().isOk());
    }
}