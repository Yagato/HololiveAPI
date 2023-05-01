package com.yagato.HololiveAPI.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yagato.HololiveAPI.model.Generation;
import com.yagato.HololiveAPI.service.GenerationService;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class GenerationControllerTest {

    private static MockHttpServletRequest request;

    @PersistenceContext
    private EntityManager entityManager;

    private final Dotenv dotenv = Dotenv.load();

    private final String HOLOLIVE_API_TOKEN = dotenv.get("HOLOLIVE_API_TOKEN");

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private GenerationService generationService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${sql.script.create.generations}")
    private String sqlInsertGeneration;

    @Value("${sql.script.delete.generations}")
    private String sqlDeleteGeneration;

    @BeforeAll
    public static void setup() {
        request = new MockHttpServletRequest();
    }

    @BeforeEach
    public void setupDatabase() {
        jdbcTemplate.execute(sqlInsertGeneration);
    }

    @AfterEach
    public void setupAfterTransaction() {
        jdbcTemplate.execute(sqlDeleteGeneration);
    }

    @DisplayName("Find All")
    @Test
    public void findAllTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/generations/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @DisplayName("Find Generation by ID")
    @Test
    public void findGenerationByIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/generations/{generationId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @DisplayName("Find Generation by Invalid ID")
    @Test
    public void findGenerationByInvalidIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/generations/{generationId}", 0))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.httpStatus", is("BAD_REQUEST")));
    }

    @DisplayName("Add Generation")
    @Test
    public void addGenerationTest() throws Exception {
        Generation generation = new Generation(0, "JP1", null);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/generations/new")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + HOLOLIVE_API_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(generation)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("JP1")));
    }

    @DisplayName("Add Generation No Credentials")
    @Test
    public void addGenerationNoCredentialsTest() throws Exception {
        Generation generation = new Generation(0, "JP1", null);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/generations/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(generation)))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("Update Generation")
    @Test
    public void updateGenerationTest() throws Exception {
        Generation generation = generationService.findByName("JP0");
        generation.setName("Update");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/generations/update")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + HOLOLIVE_API_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(generation)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Update")));
    }

    @DisplayName("Update No Credentials")
    @Test
    public void updateGenerationNoCredentialsTest() throws Exception {
        Generation generation = generationService.findByName("JP0");
        generation.setName("Update");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/generations/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(generation)))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("Delete by ID")
    @Test
    public void deleteGenerationByIdTest() throws Exception {
        int id = 1;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/generations/{generationId}", id)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + HOLOLIVE_API_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted Generation with id - " + id));
    }

    @DisplayName("Delete by Invalid ID")
    @Test
    public void deleteGenerationByInvalidIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/generations/{generationId}", 0)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + HOLOLIVE_API_TOKEN))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.httpStatus", is("BAD_REQUEST")));
    }

    @DisplayName("Delete by ID No Credentials")
    @Test
    public void deleteGenerationByIdNoCredentialsTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/generations/{generationId}", 1))
                .andExpect(status().is4xxClientError());
    }

}
