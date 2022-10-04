package ru.kochnev.technomant.SpringBoot.SpringBoot.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.kochnev.technomant.SpringBoot.SpringBoot.ApplicationTest;
import ru.kochnev.technomant.SpringBoot.models.User;
import ru.kochnev.technomant.SpringBoot.modelsDTO.UserDTO;
import ru.kochnev.technomant.SpringBoot.repositories.UserRepository;

import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
class UserControllerTest extends ApplicationTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    void tearDown() {
        cleanAndMigrate();
    }

    @Test
    void createNewUser() throws Exception {

        User newUser = createTestUser("new");

        String expectedContent = objectMapper.writeValueAsString(newUser);

        this.mockMvc.perform(post("/user/registration")
                        .content(expectedContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getById() throws Exception{

        User userTest = createTestUser("writer");
        UserDTO userDtoTest = modelMapper.map(userTest, UserDTO.class);

        String expectedContent = objectMapper.writeValueAsString(userDtoTest);

        this.mockMvc.perform(get("/user/" + userTest.getId().toString())
                        .with(httpBasic("admin", "admin")))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedContent));
    }

    @Test
    void update() throws Exception {

        User userTest = createTestUser("writer");

        User updatedUser = userTest
                .setName("updated user name");

        String requestContent = objectMapper.writeValueAsString(updatedUser);

        this.mockMvc.perform(put("/user")
                        .with(httpBasic("writer", "admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().isOk());

        User actualUser = userRepository.findById(updatedUser.getId());

        User expectedUser = userTest
                .setName("updated user name")
                .setPassword(actualUser.getPassword())
                .setPasswordConfirm(actualUser.getPasswordConfirm());

        Assertions.assertEquals(expectedUser, actualUser);
    }

    @Test
    void deleteById() throws Exception {

        User userTest = createTestUser("writer");

        mockMvc.perform(delete("/user/" + userTest.getId().toString())
                        .with(httpBasic("writer", "admin")))
                .andExpect(status().isOk());
    }

    @Test
    void getByNotExistingId() throws Exception {

        UUID notExistingCourseId = UUID.randomUUID();

        this.mockMvc.perform(get("/user/" + notExistingCourseId)
                        .with(httpBasic("admin", "admin")))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("User not found"));    }

    @Test
    void createNewUserWithEmptyField() throws Exception {

        User userTest = createTestUser("writer");
        userTest.setName("");

        String requestContent = objectMapper.writeValueAsString(userTest);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/registration")
                        .content(requestContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("name of user cannot be empty "));
    }

    @Test
    void unauthorizedUser() throws Exception{

        User userTest = createTestUser("writer");

        String expectedContent = objectMapper.writeValueAsString(userTest);

        this.mockMvc.perform(post("/user")
                        .with(httpBasic("admin", "wrongPassword"))
                        .content(expectedContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void createUserWithNotMatchPasswordConfirm() throws Exception {

        User newUser = createTestUser("new")
                .setPasswordConfirm("wrong");

        String expectedContent = objectMapper.writeValueAsString(newUser);

        this.mockMvc.perform(post("/user/registration")
                        .content(expectedContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("fields 'password' and 'password confirm' do not match"));
    }
}