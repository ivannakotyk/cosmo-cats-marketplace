package ua.kpi.ivanka.marketplace.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void createValidProduct_returns201() throws Exception {
        String body = "{\"name\":\"galaxy yarn\",\"price\":12.5,\"description\":\"anti-gravity yarn\"}";
        mvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("galaxy yarn"));
    }

    @Test
    void createInvalidProduct_returnsProblemDetails400() throws Exception {
        String body = "{\"name\":\"yarn\",\"price\":0}";
        mvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/problem+json"))
                .andExpect(jsonPath("$.title").value("Validation failed"))
                .andExpect(jsonPath("$.status").value(400));
    }
}
