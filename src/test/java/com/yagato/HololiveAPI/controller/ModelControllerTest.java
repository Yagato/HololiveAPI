package com.yagato.HololiveAPI.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yagato.HololiveAPI.model.Model;
import com.yagato.HololiveAPI.service.ModelService;
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
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class ModelControllerTest {

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
    ModelService modelService;

    @Autowired
    private TalentService talentService;

    @Value("${sql.script.create.model}")
    private String sqlInsertModel;

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
        jdbcTemplate.execute(sqlInsertModel);
    }

    @AfterEach
    public void setupAfterTransaction() {
        jdbcTemplate.execute(sqlDeleteTalent);
    }

    @DisplayName("Find Model by Talent ID")
    @Test
    public void findModelByTalentIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/models/{talentId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].name", is("Main")));
    }

    @DisplayName("Find Model by Invalid Talent ID")
    @Test
    public void findModelByInvalidTalentIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/models/{talentId}", 0))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.httpStatus", is("BAD_REQUEST")));
    }

    @DisplayName("Add Model")
    @Test
    public void addModel() throws Exception {
        Resource fileResource = new ClassPathResource("test.jpg");
        MockMultipartFile image = new MockMultipartFile("image", fileResource.getFilename(),
                MediaType.IMAGE_JPEG_VALUE, fileResource.getInputStream());

        Model model = new Model(0, "New Main", null, null, null, null);
        String modelJson = objectMapper.writeValueAsString(model);
        MockMultipartFile modelPartFile = new MockMultipartFile("model", "",
                MediaType.APPLICATION_JSON_VALUE, modelJson.getBytes());

        MockPart talentNamePart = new MockPart("talent_name", "Hoshimachi Suisei".getBytes());

        mockMvc.perform(multipart("/api/models/new")
                        .file(image)
                        .file(modelPartFile)
                        .part(talentNamePart)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + HOLOLIVE_API_TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("New Main")));
    }

    // todo: test adding a model with an existing illustrator
    // todo: test adding a model with a new illustrator
    // todo: test adding a model with an existing rigger
    // todo: test adding a model with a new rigger
    // todo: test adding a model without an image
    // todo: test adding a model without credentials
    // todo: test updating a model
    // todo: test updating a model without an image
    // todo: test updating a model without credentials
    // todo: test deleting a model
    // todo: test deleting a model with an invalid talent id
    // todo: test deleting a model without credentials

}
