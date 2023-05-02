package com.yagato.HololiveAPI.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yagato.HololiveAPI.model.Illustrator;
import com.yagato.HololiveAPI.model.Model;
import com.yagato.HololiveAPI.model.Rigger;
import com.yagato.HololiveAPI.service.IllustratorService;
import com.yagato.HololiveAPI.service.ModelService;
import com.yagato.HololiveAPI.service.RiggerService;
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
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
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

    @Autowired
    private IllustratorService illustratorService;

    @Autowired
    private RiggerService riggerService;

    @Value("${sql.script.create.model}")
    private String sqlInsertModel;

    @Value("${sql.script.create.talent}")
    private String sqlInsertTalent;

    @Value("${sql.script.create.illustrator}")
    private String sqlInsertIllustrator;

    @Value("${sql.script.create.model_illustrators}")
    private String sqlInsertModelIllustrator;

    @Value("${sql.script.create.model_riggers}")
    private String sqlInsertModelRigger;

    @Value("${sql.script.create.rigger}")
    private String sqlInsertRigger;

    @Value("${sql.script.delete.talent}")
    private String sqlDeleteTalent;

    @Value("${sql.script.delete.illustrator}")
    private String sqlDeleteIllustrator;

    @Value("${sql.script.delete.rigger}")
    private String sqlDeleteRigger;

    @BeforeAll
    public static void setup() {
        request = new MockHttpServletRequest();
    }

    @BeforeEach
    public void setupDatabase() {
        jdbcTemplate.execute(sqlInsertTalent);
        jdbcTemplate.execute(sqlInsertModel);
        jdbcTemplate.execute(sqlInsertIllustrator);
        jdbcTemplate.execute(sqlInsertRigger);
//        jdbcTemplate.execute(sqlInsertModelIllustrator);
//        jdbcTemplate.execute(sqlInsertModelRigger);
    }

    @AfterEach
    public void setupAfterTransaction() {
        jdbcTemplate.execute(sqlDeleteTalent);
        jdbcTemplate.execute(sqlDeleteIllustrator);
        jdbcTemplate.execute(sqlDeleteRigger);
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
    public void addModelTest() throws Exception {
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

    @DisplayName("Add Model with an Existing Illustrator")
    @Test
    public void addModelExistingIllustratorTest() throws Exception {
        Resource fileResource = new ClassPathResource("test.jpg");
        MockMultipartFile image = new MockMultipartFile("image", fileResource.getFilename(),
                MediaType.IMAGE_JPEG_VALUE, fileResource.getInputStream());

        List<Illustrator> illustratorsList = new ArrayList<>();
        Illustrator illustrator = illustratorService.findByName("Teshima Nari");
        illustratorsList.add(illustrator);
        Model model = new Model(0, "New Main", null, null, illustratorsList, null);
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
                .andExpect(jsonPath("$.illustrators.[0].name", is("Teshima Nari")));
    }

    @DisplayName("Add Model with a New Illustrator")
    @Test
    public void addModelNewIllustratorTest() throws Exception {
        Resource fileResource = new ClassPathResource("test.jpg");
        MockMultipartFile image = new MockMultipartFile("image", fileResource.getFilename(),
                MediaType.IMAGE_JPEG_VALUE, fileResource.getInputStream());

        List<Illustrator> illustratorsList = new ArrayList<>();
        Illustrator illustrator = new Illustrator(0, "TEST", null);
        illustratorsList.add(illustrator);
        Model model = new Model(0, "New Main", null, null, illustratorsList, null);
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
                .andExpect(jsonPath("$.illustrators.[0].name", is("TEST")));
    }

    @DisplayName("Add Model with an Existing Rigger")
    @Test
    public void addModelExistingRiggerTest() throws Exception {
        Resource fileResource = new ClassPathResource("test.jpg");
        MockMultipartFile image = new MockMultipartFile("image", fileResource.getFilename(),
                MediaType.IMAGE_JPEG_VALUE, fileResource.getInputStream());

        List<Rigger> riggerList = new ArrayList<>();
        Rigger rigger = riggerService.findByName("rariemonn");
        riggerList.add(rigger);
        Model model = new Model(0, "New Main", null, null, null, riggerList);
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
                .andExpect(jsonPath("$.riggers.[0].name", is("rariemonn")));
    }

    @DisplayName("Add Model with a New Rigger")
    @Test
    public void addModelNewRiggerTest() throws Exception {
        Resource fileResource = new ClassPathResource("test.jpg");
        MockMultipartFile image = new MockMultipartFile("image", fileResource.getFilename(),
                MediaType.IMAGE_JPEG_VALUE, fileResource.getInputStream());

        List<Rigger> riggerList = new ArrayList<>();
        Rigger rigger = new Rigger(0, "TEST", null);
        riggerList.add(rigger);
        Model model = new Model(0, "New Main", null, null, null, riggerList);
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
                .andExpect(jsonPath("$.riggers.[0].name", is("TEST")));
    }

    @DisplayName("Add Model Without an Image")
    @Test
    public void addModelWithoutImageTest() throws Exception {
        Model model = new Model(0, "New Main", null, null, null, null);
        String modelJson = objectMapper.writeValueAsString(model);
        MockMultipartFile modelPartFile = new MockMultipartFile("model", "",
                MediaType.APPLICATION_JSON_VALUE, modelJson.getBytes());

        MockPart talentNamePart = new MockPart("talent_name", "Hoshimachi Suisei".getBytes());

        mockMvc.perform(multipart("/api/models/new")
                        .file(modelPartFile)
                        .part(talentNamePart)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + HOLOLIVE_API_TOKEN))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("Add Model without Model JSON")
    @Test
    public void addModelWithoutModelJsonTest() throws Exception {
        Resource fileResource = new ClassPathResource("test.jpg");
        MockMultipartFile image = new MockMultipartFile("image", fileResource.getFilename(),
                MediaType.IMAGE_JPEG_VALUE, fileResource.getInputStream());

        MockPart talentNamePart = new MockPart("talent_name", "Hoshimachi Suisei".getBytes());

        mockMvc.perform(multipart("/api/models/new")
                        .file(image)
                        .part(talentNamePart)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + HOLOLIVE_API_TOKEN))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("Add Model without the Talent's Name")
    @Test
    public void addModelWithoutTalentNameTest() throws Exception {
        Resource fileResource = new ClassPathResource("test.jpg");
        MockMultipartFile image = new MockMultipartFile("image", fileResource.getFilename(),
                MediaType.IMAGE_JPEG_VALUE, fileResource.getInputStream());

        Model model = new Model(0, "New Main", null, null, null, null);
        String modelJson = objectMapper.writeValueAsString(model);
        MockMultipartFile modelPartFile = new MockMultipartFile("model", "",
                MediaType.APPLICATION_JSON_VALUE, modelJson.getBytes());

        mockMvc.perform(multipart("/api/models/new")
                        .file(image)
                        .file(modelPartFile)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + HOLOLIVE_API_TOKEN))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("Add Model Invalid Talent Name")
    @Test
    public void addModelInvalidTalentNameTest() throws Exception {
        Resource fileResource = new ClassPathResource("test.jpg");
        MockMultipartFile image = new MockMultipartFile("image", fileResource.getFilename(),
                MediaType.IMAGE_JPEG_VALUE, fileResource.getInputStream());

        Model model = new Model(0, "New Main", null, null, null, null);
        String modelJson = objectMapper.writeValueAsString(model);
        MockMultipartFile modelPartFile = new MockMultipartFile("model", "",
                MediaType.APPLICATION_JSON_VALUE, modelJson.getBytes());

        MockPart talentNamePart = new MockPart("talent_name", "0".getBytes());

        mockMvc.perform(multipart("/api/models/new")
                        .file(image)
                        .file(modelPartFile)
                        .part(talentNamePart)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + HOLOLIVE_API_TOKEN))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.httpStatus", is("BAD_REQUEST")));
    }

    @DisplayName("Add Model without Credentials")
    @Test
    public void addModelNoCredentialsTest() throws Exception {
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
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("Update Model")
    @Test
    public void updateModelTest() throws Exception {
        Resource fileResource = new ClassPathResource("test-update.jpg");
        MockMultipartFile image = new MockMultipartFile("image", fileResource.getFilename(),
                MediaType.IMAGE_JPEG_VALUE, fileResource.getInputStream());

        Model model = modelService.findById(1);
        model.setName("UPDATE");
        String oldURL = model.getImageURL();
        String modelJson = objectMapper.writeValueAsString(model);
        MockMultipartFile modelPartFile = new MockMultipartFile("model", "",
                MediaType.APPLICATION_JSON_VALUE, modelJson.getBytes());

        MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart(
                "/api/models/update");
        builder.with(r -> {
            r.setMethod("PUT");
            return r;
        });

        mockMvc.perform(builder
                        .file(image)
                        .file(modelPartFile)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + HOLOLIVE_API_TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("UPDATE")));

        Model updatedModel = modelService.findById(1);
        String newURL = updatedModel.getImageURL();

        assertNotEquals(newURL, oldURL);
    }

    @DisplayName("Update Model without an Image")
    @Test
    public void updateModelWithoutImageTest() throws Exception {
        Model model = modelService.findById(1);
        model.setName("UPDATE");
        String oldURL = model.getImageURL();
        String modelJson = objectMapper.writeValueAsString(model);
        MockMultipartFile modelPartFile = new MockMultipartFile("model", "",
                MediaType.APPLICATION_JSON_VALUE, modelJson.getBytes());

        MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart(
                "/api/models/update");
        builder.with(r -> {
            r.setMethod("PUT");
            return r;
        });

        mockMvc.perform(builder
                        .file(modelPartFile)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + HOLOLIVE_API_TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("UPDATE")));

        Model updatedModel = modelService.findById(1);
        String newURL = updatedModel.getImageURL();

        assertEquals(newURL, oldURL);
    }

    @DisplayName("Update Model Invalid Model")
    @Test
    public void updateModelInvalidModelTest() throws Exception {
        Model model = new Model(2, "TEST MODEL", null, null, null, null);
        String modelJson = objectMapper.writeValueAsString(model);
        MockMultipartFile modelPartFile = new MockMultipartFile("model", "",
                MediaType.APPLICATION_JSON_VALUE, modelJson.getBytes());

        MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart(
                "/api/models/update");
        builder.with(r -> {
            r.setMethod("PUT");
            return r;
        });

        mockMvc.perform(builder
                        .file(modelPartFile)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + HOLOLIVE_API_TOKEN))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.httpStatus", is("BAD_REQUEST")));
    }

    @DisplayName("Update Model without Model JSON")
    @Test
    public void updateModelNoModelJsonTest() throws Exception {
        Resource fileResource = new ClassPathResource("test-update.jpg");
        MockMultipartFile image = new MockMultipartFile("image", fileResource.getFilename(),
                MediaType.IMAGE_JPEG_VALUE, fileResource.getInputStream());

        MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart(
                "/api/models/update");
        builder.with(r -> {
            r.setMethod("PUT");
            return r;
        });

        mockMvc.perform(builder
                        .file(image)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + HOLOLIVE_API_TOKEN))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("Update Model without Credentials")
    @Test
    public void updateModelNoCredentialsTest() throws Exception {
        Resource fileResource = new ClassPathResource("test-update.jpg");
        MockMultipartFile image = new MockMultipartFile("image", fileResource.getFilename(),
                MediaType.IMAGE_JPEG_VALUE, fileResource.getInputStream());

        Model model = modelService.findById(1);
        model.setName("UPDATE");
        String modelJson = objectMapper.writeValueAsString(model);
        MockMultipartFile modelPartFile = new MockMultipartFile("model", "",
                MediaType.APPLICATION_JSON_VALUE, modelJson.getBytes());

        MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart(
                "/api/models/update");
        builder.with(r -> {
            r.setMethod("PUT");
            return r;
        });

        mockMvc.perform(builder
                        .file(image)
                        .file(modelPartFile)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("Delete by ID")
    @Test
    public void deleteModelByIdTest() throws Exception {
        int id = 1;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/models/{modelId}", id)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + HOLOLIVE_API_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted model " + id));
    }

    @DisplayName("Delete by Invalid ID")
    @Test
    public void deleteModelByInvalidIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/models/{modelId}", 0)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + HOLOLIVE_API_TOKEN))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.httpStatus", is("BAD_REQUEST")));
    }

    @DisplayName("Delete by ID No Credentials")
    @Test
    public void deleteModelByIdNoCredentialsTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/models/{modelId}", 1))
                .andExpect(status().is4xxClientError());
    }

}
