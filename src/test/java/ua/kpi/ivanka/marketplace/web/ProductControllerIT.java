package ua.kpi.ivanka.marketplace.web;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ua.kpi.ivanka.marketplace.AbstractIT;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.reset;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@DisplayName("Product Controller Integration Tests")
@Tag("product-service")
public class ProductControllerIT extends AbstractIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void clearWiremock() {
        reset();
    }

    private String jsonCreate(String name, String price) throws Exception {
        Map<String, Object> body = Map.of(
                "name", name,
                "price", new BigDecimal(price),
                "description", "desc"
        );
        return objectMapper.writeValueAsString(body);
    }

    @Test
    @SneakyThrows
    @DisplayName("POST /products -> should create product with ID and return 201")
    void createProduct() {
        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCreate("Galaxy Salmon", "12.34")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Galaxy Salmon"))
                .andExpect(jsonPath("$.price").value(12.34));
    }

    @Test
    @SneakyThrows
    @DisplayName("POST /products -> should return 400 with ProblemDetail when validation fails")
    void validationError() {
        Map<String, Object> invalid = Map.of("name", "ab", "price", new BigDecimal("0.00"));

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.detail").exists())
                .andExpect(jsonPath("$.instance").value("/api/v1/products"));
    }

    @Test
    @SneakyThrows
    @DisplayName("GET /products -> should return 200 with product list")
    void listProducts() {
        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @DisplayName("GET /products/{id} -> should return 404 ProblemDetail when product not found")
    void getNotFound() {
        UUID id = UUID.randomUUID();

        mockMvc.perform(get("/api/v1/products/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.instance").value(String.format("/api/v1/products/%s", id)));
    }

    @Test
    @SneakyThrows
    @DisplayName("PUT /products/{id} -> should update product and return 200 OK")
    void updateProduct() {
        String createBody = jsonCreate("Galaxy Quasar Roll", "8.90");
        String response = mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBody))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String productId = objectMapper.readTree(response).get("id").asText();

        Map<String, Object> update = Map.of(
                "name", "Galaxy Quasar Roll XL",
                "price", new BigDecimal("9.90")
        );

        mockMvc.perform(put("/api/v1/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productId))
                .andExpect(jsonPath("$.name").value("Galaxy Quasar Roll XL"))
                .andExpect(jsonPath("$.price").value(9.90));
    }

    @Test
    @SneakyThrows
    @DisplayName("DELETE /products/{id} -> should return 204 and be idempotent")
    void deleteProduct() {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/api/v1/products/{id}", id))
                .andExpect(status().isNoContent())
                .andExpect(result ->
                        System.out.printf("Product with ID %s successfully deleted (idempotent)%n", id));

        mockMvc.perform(delete("/api/v1/products/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    @SneakyThrows
    @DisplayName("GET /products/rates -> should return 200 with stubbed WireMock response")
    void ratesEndpoint() {
        WireMock.stubFor(WireMock.get("/api/v1/rates")
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBody("{\"usd\":40.1,\"eur\":42.0}")));

        mockMvc.perform(get("/api/v1/products/rates"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usd").value(40.1))
                .andExpect(jsonPath("$.eur").value(42.0))
                .andExpect(result ->
                        System.out.printf("Cosmic rates retrieved: USD=%.1f, EUR=%.1f%n", 40.1, 42.0));
    }
}
