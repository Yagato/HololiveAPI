package com.yagato.HololiveAPI.service;

import com.yagato.HololiveAPI.exception.ApiRequestException;
import com.yagato.HololiveAPI.model.Generation;
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
public class GenerationServiceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private GenerationService generationService;

    @Autowired
    private TalentService talentService;

    @Value("${sql.script.create.talent}")
    private String sqlInsertTalent;

    @Value("${sql.script.create.generations}")
    private String sqlInsertGeneration;

    @Value("${sql.script.create.talents_generations}")
    private String sqlInsertTalentGeneration;

    @Value("${sql.script.delete.talent}")
    private String sqlDeleteTalent;

    @Value("${sql.script.delete.generations}")
    private String sqlDeleteGeneration;

    @BeforeEach
    public void setupDatabase() {
        jdbcTemplate.execute(sqlInsertTalent);
        jdbcTemplate.execute(sqlInsertGeneration);
        jdbcTemplate.execute(sqlInsertTalentGeneration);
    }

    @AfterEach
    public void setupAfterTransaction() {
        jdbcTemplate.execute(sqlDeleteTalent);
        jdbcTemplate.execute(sqlDeleteGeneration);
    }

    @DisplayName("Find All")
    @Test
    public void findAllTest() {
        List<Generation> generations = generationService.findAll();

        assertEquals(1, generations.size());
    }

    @DisplayName("Find by ID")
    @Test
    public void finByIdTest() {
        Generation generation = generationService.findById(1);
        assertEquals(1, generation.getId());
    }

    @DisplayName("Find by Invalid ID")
    @Test
    public void findByInvalidIdTest() {
        assertThrows(ApiRequestException.class, () -> {generationService.findById(0);});
    }

    @DisplayName("Find by Name")
    @Test
    public void findByNameTest() {
        Generation generation = generationService.findByName("JP0");
        assertEquals("JP0", generation.getName());
    }

    @DisplayName("Find by Invalid Name")
    @Test
    public void findByInvalidNameTest() {
        Generation generation = generationService.findByName("0");
        assertNull(generation);
    }

    @DisplayName("Save")
    @Test
    public void saveTest() {
        Generation generation = new Generation(0, "JP1", null);

        Generation persistedGeneration = generationService.save(generation);

        assertEquals(generation.getName(), persistedGeneration.getName());
    }

    @DisplayName("Delete by ID")
    @Test
    public void deleteByIdTest() {
        generationService.deleteById(1);

        assertThrows(ApiRequestException.class, () -> {generationService.findById(1);});
    }

}
