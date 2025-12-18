package ua.kpi.ivanka.marketplace.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ua.kpi.ivanka.marketplace.AbstractIT;
import ua.kpi.ivanka.marketplace.dto.CategoryDTO;
import ua.kpi.ivanka.marketplace.repository.CategoryRepository;
import ua.kpi.ivanka.marketplace.repository.ProductRepository;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Category Controller Integration Tests")
class CategoryControllerIT extends AbstractIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    @SneakyThrows
    @DisplayName("Should create category successfully")
    void shouldCreateCategory() {
        CategoryDTO dto = CategoryDTO.builder()
                .name("Cosmic Food")
                .description("Food for space cats")
                .build();

        mockMvc.perform(post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Cosmic Food"));
    }

    @Test
    @SneakyThrows
    @DisplayName("Should fail when creating duplicate category (DB Constraint Check)")
    void shouldFailOnDuplicateCategory() {
        CategoryDTO dto = CategoryDTO.builder()
                .name("UniqueName")
                .description("First one")
                .build();

        mockMvc.perform(post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").exists());
    }

    @Test
    @SneakyThrows
    @DisplayName("Should search categories by keyword")
    void shouldSearchCategories() {
        CategoryDTO c1 = CategoryDTO.builder().name("Solar Systems").description("Planets and suns").build();
        CategoryDTO c2 = CategoryDTO.builder().name("Void").description("Empty space").build();

        mockMvc.perform(post("/api/v1/categories").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(c1)));
        mockMvc.perform(post("/api/v1/categories").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(c2)));

        mockMvc.perform(get("/api/v1/categories/search")
                        .param("keyword", "sun"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Solar Systems"));
    }

    @Test
    @SneakyThrows
    @DisplayName("Should get category by name")
    void shouldGetCategoryByName() {
        String name = "Milky Way " + System.currentTimeMillis();
        CategoryDTO dto = CategoryDTO.builder().name(name).description("Our home").build();

        mockMvc.perform(post("/api/v1/categories").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dto)));

        mockMvc.perform(get("/api/v1/categories/name/{name}", name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    @SneakyThrows
    @DisplayName("Should return 404 when getting non-existent category")
    void shouldReturn404ForMissingCategory() {
        mockMvc.perform(get("/api/v1/categories/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").exists());
    }

    @Test
    @SneakyThrows
    @DisplayName("Should return 400 when category name is too short")
    void shouldReturn400OnInvalidName() {
        CategoryDTO dto = CategoryDTO.builder().name("A").build();

        mockMvc.perform(post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").exists());
    }
}