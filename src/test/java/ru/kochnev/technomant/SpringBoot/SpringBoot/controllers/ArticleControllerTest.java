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
import ru.kochnev.technomant.SpringBoot.models.Article;
import ru.kochnev.technomant.SpringBoot.models.Page;
import ru.kochnev.technomant.SpringBoot.models.User;
import ru.kochnev.technomant.SpringBoot.modelsDTO.ArticleDTO;
import ru.kochnev.technomant.SpringBoot.repositories.ArticleRepository;

import java.util.List;
import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
class ArticleControllerTest extends ApplicationTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    void tearDown() {
        cleanAndMigrate();
    }

    @Test
    void save() throws Exception {
        Article articleTest = createTestArticle();

        String expectedContent = objectMapper.writeValueAsString(articleTest);

        this.mockMvc.perform(post("/article")
                        .with(httpBasic("writer", "admin"))
                        .content(expectedContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getDtoById() throws Exception {
        Article articleTest = createTestArticle();
        ArticleDTO articleDtoTest = modelMapper.map(articleTest, ArticleDTO.class);

        String expectedContent = objectMapper.writeValueAsString(articleDtoTest);

        this.mockMvc.perform(get("/article/" + articleTest.getId().toString())
                        .with(httpBasic("writer", "admin")))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedContent));
    }

    @Test
    void update() throws Exception {

        Article articleTest = createTestArticle();

        User admin = createTestUser("admin")
                .setPassword("$2a$10$tSeHGqlg8d5QY7MwSusx/uyGWfYZ8daUA8cClaBibI47x9s4VpfD.")
                .setPasswordConfirm("$2a$10$tSeHGqlg8d5QY7MwSusx/uyGWfYZ8daUA8cClaBibI47x9s4VpfD.");

        Article expectedCourse = articleTest
                .setName("updated article name")
                .setContent("updated content");

        String requestContent = objectMapper.writeValueAsString(expectedCourse);

        this.mockMvc.perform(put("/article")
                        .with(httpBasic("writer", "admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().isOk());

        Article actualLesson = articleRepository.findById(expectedCourse.getId());

        Assertions.assertEquals(expectedCourse, actualLesson);
    }

    @Test
    void deleteById() throws Exception {
        Article articleTest = createTestArticle();

        mockMvc.perform(delete("/article/" + articleTest.getId().toString())
                        .with(httpBasic("writer", "admin")))
                .andExpect(status().isOk());

    }

    @Test
    void getByNotExistingId() throws Exception {

        UUID notExistingCourseId = UUID.randomUUID();

        this.mockMvc.perform(get("/article/" + notExistingCourseId)
                        .with(httpBasic("admin", "admin")))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("Article not found"));    }

    @Test
    public void saveWithEmptyField() throws Exception {

        Article articleTest = createTestArticle();
        articleTest.setName("");

        String requestContent = objectMapper.writeValueAsString(articleTest);

        mockMvc.perform(MockMvcRequestBuilders.post("/article")
                        .content(requestContent)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(httpBasic("admin", "admin")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("name of article cannot be empty "));
    }

    @Test
    public void saveWithLongName() throws Exception {

        Article articleTest = createTestArticle();
        articleTest.setName("0123456789101112131415161718192021222324252627282930313233343536373839404142434445464748495051525354555657585960616263646566676869707172737475767778798081828384858687888990919293949596979899100101");

        String requestContent = objectMapper.writeValueAsString(articleTest);

        mockMvc.perform(MockMvcRequestBuilders.post("/article")
                        .content(requestContent)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(httpBasic("admin", "admin")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("the name should not exceed 100 characters"));
    }

    @Test
    void getPaginated() throws Exception {
        Article articleTest = createTestArticle();

        ArticleDTO articleDtoTest = modelMapper.map(articleTest, ArticleDTO.class);
        articleDtoTest.setContent(null);

        List<ArticleDTO> paginatedList = List.of(articleDtoTest);

        Page page = new Page()
                .setCurrentPage(paginatedList)
                .setNumOfNextPage(2);


        String expectedContent = objectMapper.writeValueAsString(page);

        this.mockMvc.perform(get("/article/")
                        .with(httpBasic("writer", "admin"))
                        .param("page", "2")
                        .param("size", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedContent));
    }

    @Test
    void unauthorizedUser() throws Exception{

        Article articleTest = createTestArticle();

        String expectedContent = objectMapper.writeValueAsString(articleTest);

        this.mockMvc.perform(post("/article")
                        .with(httpBasic("admin", "wrongPassword"))
                        .content(expectedContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}
