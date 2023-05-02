package com.yagato.HololiveAPI.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yagato.HololiveAPI.model.Talent;
import com.yagato.HololiveAPI.service.TalentService;
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
public class TalentControllerTest {

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
    private TalentService talentService;

    @Value("${sql.script.create.talent}")
    private String sqlInsertTalent;

    @Value("${sql.script.delete.talent}")
    private String sqlDeleteTalent;

    @BeforeAll
    public static void setup() {
        request = new MockHttpServletRequest();
    }

    @BeforeEach
    public void setupDatabase() {
        jdbcTemplate.execute(sqlInsertTalent);
    }

    @AfterEach
    public void setupAfterTransaction() {
        jdbcTemplate.execute(sqlDeleteTalent);
    }

    @DisplayName("Find All")
    @Test
    public void findAllTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/talents/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @DisplayName("Find by Name")
    @Test
    public void findByNameTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/talents/name={name}", "Hoshimachi Suisei"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Hoshimachi Suisei")));
    }

    @DisplayName("Find by Invalid Name")
    @Test
    public void findByInvalidNameTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/talents/name={name}", "0"))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.httpStatus", is("BAD_REQUEST")));
    }

    @DisplayName("Find by Channel ID")
    @Test
    public void findByChannelIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/talents/channelId={channelId}", "UC5CwaMl1eIgY8h02uZw7u8A"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Hoshimachi Suisei")));
    }

    @DisplayName("Find by Invalid Channel ID")
    @Test
    public void findByInvalidChannelIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/talents/channelId={channelId}", "0"))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.httpStatus", is("BAD_REQUEST")));
    }

    @DisplayName("Add Talent")
    @Test
    public void addTalentTest() throws Exception {
        Talent talent = new Talent(
                0,
                "Test",
                null,
                null,
                null,
                18,
                null,
                null,
                180,
                48.8,
                100,
                "channelId",
                null,
                "oshi",
                null,
                null,
                "catchphrase",
                null,
                true,
                null
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/api/talents/new")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + HOLOLIVE_API_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(talent)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Test")));
    }

    @DisplayName("Add Talent No Credentials")
    @Test
    public void addTalentNoCredentialsTest() throws Exception {
        Talent talent = new Talent(
                0,
                "Test",
                null,
                null,
                null,
                18,
                null,
                null,
                180,
                48.8,
                100,
                "channelId",
                null,
                "oshi",
                null,
                null,
                "catchphrase",
                null,
                true,
                null
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/api/talents/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(talent)))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("Update Talent")
    @Test
    public void updateTalentTest() throws Exception {
        Talent talent = talentService.findByName("Hoshimachi Suisei");
        talent.setName("Suityan");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/talents/update")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + HOLOLIVE_API_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(talent)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Suityan")));
    }

    @DisplayName("Update Talent No Credentials")
    @Test
    public void updateTalentNoCredentialsTest() throws Exception {
        Talent talent = talentService.findByName("Hoshimachi Suisei");
        talent.setName("Suityan");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/talents/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(talent)))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("Delete Talent by Name")
    @Test
    public void deleteTalentByName() throws Exception {
        String talentName = "Hoshimachi Suisei";
        String urlTalentName = "Hoshimachi_Suisei";

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/talents/{name}", urlTalentName)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + HOLOLIVE_API_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted talent " + talentName));
    }

    @DisplayName("Delete Talent by Invalid Name")
    @Test
    public void deleteTalentByInvalidName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/talents/{name}", "0")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + HOLOLIVE_API_TOKEN))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.httpStatus", is("BAD_REQUEST")));
    }

    @DisplayName("Delete Talent by Name No Credentials")
    @Test
    public void deleteTalentByNameNoCredentials() throws Exception {
        String urlTalentName = "Hoshimachi_Suisei";

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/talents/{name}", urlTalentName))
                .andExpect(status().is4xxClientError());
    }

}
