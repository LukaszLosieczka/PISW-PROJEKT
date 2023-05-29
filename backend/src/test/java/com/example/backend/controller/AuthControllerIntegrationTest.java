package com.example.backend.controller;

import com.example.backend.IntegrationTestBase;
import com.example.backend.model.Role;
import com.example.backend.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerIntegrationTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnAuthTokensWhenLogin() throws Exception{
        //given
        Role role = existingRole(Role.builder().name("ROLE_USER").build());
        User user = existingUser(User.builder()
                .name("kowalski")
                .surname("jan")
                .login("janek123")
                .password("$2a$10$iEmh3dR5UGxLnVF0bv4cVeGnukOrDITftURUxVeHstQnCBeQhbUlK")//123456
                .role(role)
                .build());
        String creationRequest = """
                                 {
                                    "login": "%s",
                                    "password": "%s"
                                 }
                                 """.formatted(user.getLogin(), "123456");

        // when then
        mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(creationRequest))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").isNotEmpty())
                .andExpect(jsonPath("$.refresh_token").isNotEmpty());
    }

    @Test
    void shouldReturnBadRequestWhenLoginWithWrongLogin() throws Exception{
        //given
        Role role = existingRole(Role.builder().name("ROLE_USER").build());
        existingUser(User.builder()
                .name("kowalski")
                .surname("jan")
                .login("janek123")
                .password("$2a$10$iEmh3dR5UGxLnVF0bv4cVeGnukOrDITftURUxVeHstQnCBeQhbUlK")//123456
                .role(role)
                .build());
        String creationRequest = """
                                 {
                                    "login": "%s",
                                    "password": "%s"
                                 }
                                 """.formatted("badLogin", "123456");

        // when then
        mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(creationRequest))
                .andDo(log())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenLoginWithWrongPassword() throws Exception{
        //given
        Role role = existingRole(Role.builder().name("ROLE_USER").build());
        User user = existingUser(User.builder()
                .name("kowalski")
                .surname("jan")
                .login("janek123")
                .password("$2a$10$iEmh3dR5UGxLnVF0bv4cVeGnukOrDITftURUxVeHstQnCBeQhbUlK")//123456
                .role(role)
                .build());
        String creationRequest = """
                                 {
                                    "login": "%s",
                                    "password": "%s"
                                 }
                                 """.formatted(user.getLogin(), "badPassword");

        // when then
        mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(creationRequest))
                .andDo(log())
                .andExpect(status().isBadRequest());
    }
}