package com.example.backend.controller;

import com.example.backend.IntegrationTestBase;
import com.example.backend.model.Role;
import com.example.backend.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RegistrationControllerIntegrationTest extends IntegrationTestBase {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnCreatedAndSaveNewUserWhenRegister() throws Exception{
        //given
        existingRole(Role.builder().name("ROLE_USER").build());
        String creationRequest = """
                                 {
                                    "surname": "%s",
                                    "name": "%s",
                                    "login": "%s",
                                    "password": "%s"
                                 }
                                 """.formatted("Jan", "Kowalski", "janek123", "123456");

        // when then
        mockMvc.perform(post("/registration/register").contentType(MediaType.APPLICATION_JSON).content(creationRequest))
                .andDo(log())
                .andExpect(status().isCreated());

        List<User> users = getAllUsers();
        assertThat(users.size()).isEqualTo(1);
        assertThat(users.get(0).getLogin()).isEqualTo("janek123");
    }

    @Test
    void shouldReturnBadRequestWhenRegisterUserWithExistingLogin() throws Exception{
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
                                    "surname": "%s",
                                    "name": "%s",
                                    "login": "%s",
                                    "password": "%s"
                                 }
                                 """.formatted("Jan", "Kowalski", user.getLogin(), "123456");

        // when then
        mockMvc.perform(post("/registration/register").contentType(MediaType.APPLICATION_JSON).content(creationRequest))
                .andDo(log())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenRegisterUserWithoutName() throws Exception{
        //given
        existingRole(Role.builder().name("ROLE_USER").build());
        String creationRequest = """
                                 {
                                    "surname": "%s",
                                    "name": "%s",
                                    "login": "%s",
                                    "password": "%s"
                                 }
                                 """.formatted("Jan", "", "janek123", "123456");

        // when then
        mockMvc.perform(post("/registration/register").contentType(MediaType.APPLICATION_JSON).content(creationRequest))
                .andDo(log())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").isNotEmpty());
    }

    @Test
    void shouldReturnBadRequestWhenRegisterUserWithoutSurname() throws Exception{
        //given
        existingRole(Role.builder().name("ROLE_USER").build());
        String creationRequest = """
                                 {
                                    "surname": "%s",
                                    "name": "%s",
                                    "login": "%s",
                                    "password": "%s"
                                 }
                                 """.formatted("", "Kowalski", "janek123", "123456");

        // when then
        mockMvc.perform(post("/registration/register").contentType(MediaType.APPLICATION_JSON).content(creationRequest))
                .andDo(log())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.surname").isNotEmpty());
    }

    @Test
    void shouldReturnBadRequestWhenRegisterUserWithTooShortPassword() throws Exception{
        //given
        existingRole(Role.builder().name("ROLE_USER").build());
        String creationRequest = """
                                 {
                                    "surname": "%s",
                                    "name": "%s",
                                    "login": "%s",
                                    "password": "%s"
                                 }
                                 """.formatted("Jan", "Kowalski", "janek123", "12345");

        // when then
        mockMvc.perform(post("/registration/register").contentType(MediaType.APPLICATION_JSON).content(creationRequest))
                .andDo(log())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.password").isNotEmpty());
    }
}
