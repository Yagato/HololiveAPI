package com.yagato.HololiveAPI.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yagato.HololiveAPI.model.Authentication;
import com.yagato.HololiveAPI.model.Register;
import com.yagato.HololiveAPI.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class AuthenticationControllerTest {

    private static MockHttpServletRequest request;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    public static void setup() {
        request = new MockHttpServletRequest();
    }

    @DisplayName("Registration")
    @Test
    public void registerTest() throws Exception {
        Register register = new Register("Nia", "Teppelin", "email@test.com", "test");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(register)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @DisplayName("Authentication Invalid Credentials")
    @Test
    public void authenticationInvalidCredentialsTest() throws Exception {
        Authentication authentication = new Authentication("invalid@mail.com", "invalid");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authentication)))
                .andExpect(status().is4xxClientError());
    }

}
