package com.yagato.HololiveAPI.service;

import com.yagato.HololiveAPI.model.Illustrator;
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
public class IllustratorServiceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private IllustratorService illustratorService;

    @Value("${sql.script.create.illustrator}")
    private String sqlInsertIllustrator;

    @Value("${sql.script.delete.illustrator}")
    private String sqlDeleteIllustrator;

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
    public void findAllTest() {
        List<Illustrator> illustrators = illustratorService.findAll();

        assertEquals(1, illustrators.size());
    }

    @DisplayName("Find by ID")
    @Test
    public void findByIdTest() {
        Illustrator illustrator = illustratorService.findById(1);

        assertEquals(1, illustrator.getId());
    }

    @DisplayName("Find by Invalid ID")
    @Test
    public void findByInvalidIdTest() {
        assertNull(illustratorService.findById(0));
    }

    @DisplayName("Find by Name")
    @Test
    public void findByNameTest() {
        Illustrator illustrator = illustratorService.findByName("Teshima Nari");

        assertEquals("Teshima Nari", illustrator.getName());
    }

    @DisplayName("Find by Invalid Name")
    @Test
    public void findByInvalidNameTest() {
        assertNull(illustratorService.findByName(""));
    }

    @DisplayName("Save")
    @Test
    public void saveTest() {
        Illustrator illustrator = new Illustrator(0, "Suityan", null);

        Illustrator persistedIllustrator = illustratorService.save(illustrator);

        assertEquals(illustrator.getName(), persistedIllustrator.getName());
    }

    @DisplayName("Delete by ID")
    @Test
    public void deleteByIdTest() {
        illustratorService.deleteById(1);

        assertNull(illustratorService.findById(1));
    }

}
