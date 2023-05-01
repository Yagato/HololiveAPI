package com.yagato.HololiveAPI.service;

import com.yagato.HololiveAPI.exception.ApiRequestException;
import com.yagato.HololiveAPI.model.Model;
import com.yagato.HololiveAPI.model.Talent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("/application-test.properties")
@SpringBootTest
public class ModelServiceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

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

    @BeforeEach
    public void setupDatabase() {
        jdbcTemplate.execute(sqlInsertTalent);
        jdbcTemplate.execute(sqlInsertModel);
    }

    @AfterEach
    public void setupAfterTransaction() {
        jdbcTemplate.execute(sqlDeleteTalent);
    }

    @DisplayName("Find by Talent ID")
    @Test
    public void findByTalentIdTest() {
        List<Model> models = modelService.findByTalentId(1);

        assertEquals(1, models.size());
    }

    @DisplayName("Find by Invalid Talent ID")
    @Test
    public void findByInvalidTalentIdTest() {
        List<Model> models = modelService.findByTalentId(0);

        assertEquals(0, models.size());
    }

    @DisplayName("Find by ID")
    @Test
    public void findById() {
        Model model = modelService.findById(1);

        assertNotNull(model);
    }

    @DisplayName("Find by Invalid ID")
    @Test
    public void findByInvalidId() {
        assertThrows(ApiRequestException.class, () -> {modelService.findById(0);});
    }

    @DisplayName("Save")
    @Test
    public void saveTest() {
        Talent talent = talentService.findById(1);

        Model model = new Model(0, "Name", "link", talent, null, null);

        Model persistedModel = modelService.save(model);

        assertEquals(model.getName(), persistedModel.getName());
    }

    @DisplayName("Delete by ID")
    @Test
    public void deleteByIdTest() {
        modelService.deleteById(1);

        assertThrows(ApiRequestException.class, () -> {modelService.findById(1);});
    }

}
