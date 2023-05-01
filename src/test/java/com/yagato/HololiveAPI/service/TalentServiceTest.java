package com.yagato.HololiveAPI.service;

import com.yagato.HololiveAPI.exception.ApiRequestException;
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
public class TalentServiceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TalentService talentService;

    @Value("${sql.script.create.talent}")
    private String sqlInsertTalent;

    @Value("${sql.script.delete.talent}")
    private String sqlDeleteTalent;

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
    public void findAllTest() {
        List<Talent> talentList = talentService.findAll();

        assertEquals(1, talentList.size());
    }

    @DisplayName("Find by Name")
    @Test
    public void findByNameTest() {
        Talent talent = talentService.findByName("Hoshimachi Suisei");

        assertNotNull(talent);
    }

    @DisplayName("Find by Invalid Name")
    @Test
    public void findByInvalidNameTest() {
        Talent talent = talentService.findByName("0");

        assertNull(talent);
    }

    @DisplayName("Find by Channel ID")
    @Test
    public void findByChannelIdTest() {
        Talent talent = talentService.findByChannelId("UC5CwaMl1eIgY8h02uZw7u8A");

        assertNotNull(talent);
    }

    @DisplayName("Find by Invalid Channel ID")
    @Test
    public void findByInvalidChannelIdTest() {
        Talent talent = talentService.findByChannelId("1");

        assertNull(talent);
    }

    @DisplayName("Find by ID")
    @Test
    public void findByIdTest() {
        Talent talent = talentService.findById(1);

        assertNotNull(talent);
    }

    @DisplayName("Find by Invalid ID")
    @Test
    public void findByInvalidIdTest() {
        assertThrows(ApiRequestException.class, () -> {talentService.findById(0);});
    }

    @DisplayName("Save")
    @Test
    public void saveTest() {
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

        Talent persistedTalent = talentService.save(talent);

        assertNotNull(persistedTalent);
    }

    @DisplayName("Delete by ID")
    @Test
    public void deleteByIdTest() {
        talentService.deleteById(1);

        assertThrows(ApiRequestException.class, () -> {talentService.findById(1);});
    }

    @DisplayName("Delete by Name")
    @Test
    public void deleteByNameTest() {
        talentService.deleteByName("Hoshimachi Suisei");

        Talent talent = talentService.findByName("Hoshimachi Suisei");

        assertNull(talent);
    }

}
