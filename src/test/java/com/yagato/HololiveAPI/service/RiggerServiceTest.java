package com.yagato.HololiveAPI.service;

import com.yagato.HololiveAPI.model.Illustrator;
import com.yagato.HololiveAPI.model.Rigger;
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
public class RiggerServiceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RiggerService riggerService;

    @Value("${sql.script.create.rigger}")
    private String sqlInsertRigger;

    @Value("${sql.script.delete.rigger}")
    private String sqlDeleteRigger;

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
    public void findAllTest() {
        List<Rigger> riggers = riggerService.findAll();

        assertEquals(1, riggers.size());
    }

    @DisplayName("Find by ID")
    @Test
    public void findByIdTest() {
        Rigger rigger = riggerService.findById(1);

        assertNotNull(rigger);
    }

    @DisplayName("Find by Invalid ID")
    @Test
    public void findByInvalidIdTest() {
        Rigger rigger = riggerService.findById(0);

        assertNull(rigger);
    }

    @DisplayName("Find by Name")
    @Test
    public void findByNameTest() {
        Rigger rigger = riggerService.findByName("rariemonn");

        assertNotNull(rigger);
    }

    @DisplayName("Find by Invalid Name")
    @Test
    public void findByInvalidNameTest() {
        Rigger rigger = riggerService.findByName("0");

        assertNull(rigger);
    }

    @DisplayName("Save")
    @Test
    public void saveTest() {
        Rigger rigger = new Rigger(0, "Suityan", null);

        Rigger persistedRigger = riggerService.save(rigger);

        assertEquals(rigger.getName(), persistedRigger.getName());
    }

    @DisplayName("Delete by ID")
    @Test
    public void deleteByIdTest() {
        riggerService.deleteById(1);

        assertNull(riggerService.findById(1));
    }

}
