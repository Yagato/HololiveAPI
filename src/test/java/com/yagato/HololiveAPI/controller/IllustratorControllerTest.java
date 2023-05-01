package com.yagato.HololiveAPI.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yagato.HololiveAPI.model.Illustrator;
import com.yagato.HololiveAPI.service.IllustratorService;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class IllustratorControllerTest {

    private static MockHttpServletRequest request;

    @PersistenceContext
    private EntityManager entityManager;

    private final Dotenv dotenv = Dotenv.load();

    private final String HOLOLIVE_API_TOKEN = dotenv.get("HOLOLIVE_API_TOKEN");

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IllustratorService illustratorService;

    @Value("${sql.script.create.illustrator}")
    private String sqlInsertIllustrator;

    @Value("${sql.script.delete.illustrator}")
    private String sqlDeleteIllustrator;

    @BeforeAll
    public static void setup() {
        request = new MockHttpServletRequest();
    }

    @BeforeEach
    public void setupDatabase() {
        jdbcTemplate.execute(sqlInsertIllustrator);
    }

    @AfterEach
    public void setupAfterTransaction() {
        jdbcTemplate.execute(sqlDeleteIllustrator);
    }

    @DisplayName("Find All")
    @Test
    public void findAllTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/illustrators/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @DisplayName("Find Illustrator by ID")
    @Test
    public void findIllustratorByIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/illustrators/{illustratorId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @DisplayName("Find Illustrator by Invalid ID")
    @Test
    public void findIllustratorByInvalidIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/illustrators/{illustratorId}", 0))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.httpStatus", is("BAD_REQUEST")));
    }

    @DisplayName("Add Illustrator")
    @Test
    public void addIllustratorTest() throws Exception {
        Illustrator illustrator = new Illustrator(0, "Suityan", null);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/illustrators/new")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + HOLOLIVE_API_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(illustrator)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Suityan")));
    }

    @DisplayName("Add Illustrator No Credentials")
    @Test
    public void addIllustratorNoCredentialsTest() throws Exception {
        Illustrator illustrator = new Illustrator(0, "Suityan", null);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/illustrators/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(illustrator)))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("Update Illustrator")
    @Test
    public void updateIllustratorTest() throws Exception {
        Illustrator illustrator = illustratorService.findByName("Teshima Nari");
        illustrator.setName("Update");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/illustrators/update")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + HOLOLIVE_API_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(illustrator)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Update")));
    }

    @DisplayName("Update No Credentials")
    @Test
    public void updateIllustratorNoCredentialsTest() throws Exception {
        Illustrator illustrator = illustratorService.findByName("Teshima Nari");
        illustrator.setName("Update");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/illustrators/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(illustrator)))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("Delete by ID")
    @Test
    public void deleteIllustratorByIdTest() throws Exception {
        int id = 1;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/illustrators/{illustratorId}", id)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + HOLOLIVE_API_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted illustrator id - " + id));
    }

    @DisplayName("Delete by Invalid ID")
    @Test
    public void deleteIllustratorByInvalidIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/illustrators/{illustratorId}", 0)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + HOLOLIVE_API_TOKEN))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.httpStatus", is("BAD_REQUEST")));
    }

    @DisplayName("Delete by ID No Credentials")
    @Test
    public void deleteIllustratorByIdNoCredentialsTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/illustrators/{illustratorId}", 1))
                .andExpect(status().is4xxClientError());
    }

}
