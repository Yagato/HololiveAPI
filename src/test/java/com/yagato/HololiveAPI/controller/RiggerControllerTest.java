package com.yagato.HololiveAPI.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yagato.HololiveAPI.model.Rigger;
import com.yagato.HololiveAPI.service.RiggerService;
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
public class RiggerControllerTest {

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
    private RiggerService riggerService;

    @Value("${sql.script.create.rigger}")
    private String sqlInsertRigger;

    @Value("${sql.script.delete.rigger}")
    private String sqlDeleteRigger;

    @BeforeAll
    public static void setup() {
        request = new MockHttpServletRequest();
    }

    @BeforeEach
    public void setupDatabase() {
        jdbcTemplate.execute(sqlInsertRigger);
    }

    @AfterEach
    public void setupAfterTransaction() {
        jdbcTemplate.execute(sqlDeleteRigger);
    }

    @DisplayName("Find All")
    @Test
    public void findAllTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/riggers/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @DisplayName("Find Rigger by ID")
    @Test
    public void findIllustratorByIdTest() throws Exception {
        int id = 1;
        mockMvc.perform(MockMvcRequestBuilders.get("/api/riggers/{riggerId}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(id)));
    }

    @DisplayName("Find Rigger by Invalid ID")
    @Test
    public void findIllustratorByInvalidIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/riggers/{riggerId}", 0))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.httpStatus", is("BAD_REQUEST")));
    }

    @DisplayName("Add Rigger")
    @Test
    public void addRiggerTest() throws Exception {
        Rigger rigger = new Rigger(0, "Suityan", null);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/riggers/new")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + HOLOLIVE_API_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rigger)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Suityan")));
    }

    @DisplayName("Add Rigger No Credentials")
    @Test
    public void addRiggerNoCredentialsTest() throws Exception {
        Rigger rigger = new Rigger(0, "Suityan", null);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/riggers/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rigger)))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("Update Rigger")
    @Test
    public void updateIllustratorTest() throws Exception {
        Rigger rigger = riggerService.findByName("rariemonn");
        rigger.setName("Update");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/riggers/update")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + HOLOLIVE_API_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rigger)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Update")));
    }

    @DisplayName("Update Rigger No Credentials")
    @Test
    public void updateIllustratorNoCredentialsTest() throws Exception {
        Rigger rigger = riggerService.findByName("rariemonn");
        rigger.setName("Update");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/riggers/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rigger)))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("Delete by ID")
    @Test
        public void deleteRiggerByIdTest() throws Exception {
        int id = 1;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/riggers/{riggerId}", id)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + HOLOLIVE_API_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted rigger id - " + id));
    }

    @DisplayName("Delete by Invalid ID")
    @Test
    public void deleteRiggerByInvalidIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/riggers/{riggerId}", 0)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + HOLOLIVE_API_TOKEN))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.httpStatus", is("BAD_REQUEST")));
    }

    @DisplayName("Delete by ID No Credentials")
    @Test
    public void deleteRiggerByIdNoCredentialsTest() throws Exception {
        int id = 1;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/riggers/{riggerId}", id))
                .andExpect(status().is4xxClientError());
    }

}
