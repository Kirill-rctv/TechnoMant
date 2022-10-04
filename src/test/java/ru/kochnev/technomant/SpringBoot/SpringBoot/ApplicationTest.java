package ru.kochnev.technomant.SpringBoot.SpringBoot;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;
import ru.kochnev.technomant.SpringBoot.models.Article;
import ru.kochnev.technomant.SpringBoot.models.Role;
import ru.kochnev.technomant.SpringBoot.models.User;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootTest
public class ApplicationTest {

    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:11.1");

    static {
        Startables.deepStart(Stream.of(postgresContainer)).join();
        System.setProperty("spring.datasource.url", postgresContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgresContainer.getUsername());
        System.setProperty("spring.datasource.password", postgresContainer.getPassword());
    }

    @Autowired
    private Flyway flyway;

    public void cleanAndMigrate() {
        flyway.clean();
        flyway.migrate();
    }





    public User createTestUser(String role) {

        String adminId = "3dccddc9-a5dd-459d-b476-cd7e42cba8eb";
        String writerId = "344d15a3-2723-4eee-8df9-6dae013bf046";

        Role roleAdmin = new Role(UUID.fromString("0f18acb3-5d62-4219-98fd-a229c25f4499"), Role.RoleName.ROLE_ADMIN);
        Role roleWriter = new Role(UUID.fromString("4296f816-53b6-4b19-94ff-f2a6768d38a7"), Role.RoleName.ROLE_WRITER);

        List<Role> roleList = new ArrayList<>();

        User newUser = new User(
                "new user",
                "newuser",
                "newuser");

        switch (role) {
            case "new":
                return newUser;
            case "writer":
                roleList.add(roleWriter);
                return newUser
                        .setId(UUID.fromString(writerId))
                        .setName("writer")
                        .setPassword("writer")
                        .setPasswordConfirm("writer")
                        .setActive(1)
                        .setRoles(roleList);
            case "admin":
                roleList.add(roleAdmin);
                roleList.add(roleWriter);
                return newUser
                        .setId(UUID.fromString(adminId))
                        .setName("admin")
                        .setPassword("admin")
                        .setPasswordConfirm("admin")
                        .setActive(1)
                        .setRoles(roleList);
            default:
                return null;
        }
    }

    public Article createTestArticle() {

        String articleId = "8dcf4ef3-f809-4985-8fd0-ba1f04b2cfdd";
        User writer = createTestUser("writer");
        User admin = createTestUser("admin");

        Article testArticle = new Article()
                .setId(UUID.fromString(articleId))
                .setAuthorId(writer.getId())
                .setName("test article")
                .setContent("test content")
                .setDatePublishing(OffsetDateTime.parse("2022-10-04T13:09:00Z"));

        return testArticle;
    }
}
