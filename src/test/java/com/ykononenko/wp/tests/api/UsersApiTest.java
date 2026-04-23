package com.ykononenko.wp.tests.api;

import com.ykononenko.wp.api.UsersApi;
import com.ykononenko.wp.core.BaseTest;
import com.ykononenko.wp.model.User;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.SoftAssertions;

import static org.assertj.core.api.Assertions.assertThat;

@Feature("Users API")
public class UsersApiTest extends BaseTest {

    private UsersApi usersApi;
    private Faker faker;
    private Integer createdUserId;
    private SoftAssertions softAssertions = new SoftAssertions();

    @BeforeEach
    void initApi() {
        usersApi = new UsersApi(
                config.getProperty("wp.base.url"),
                config.getProperty("wp.username"),
                config.getProperty("wp.password")
        );
        faker = new Faker();
    }

    @Test
    @DisplayName("Создание пользователя через API")
    @Description("Проверяет, что пользователь успешно создается и возвращает корректные данные")
    void createUserTest() {
        User newUser = User.builder()
                .username(faker.name().username())
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .build();

        Response response = usersApi.createUser(newUser);

        assertThat(response.statusCode()).isEqualTo(201);

        User createdUser = response.as(User.class);
        createdUserId = createdUser.getId();

        softAssertions.assertThat(createdUser.getId())
                .as("User ID")
                .isNotNull();
        softAssertions.assertThat(createdUser.getUsername())
                .as("Username")
                .isEqualTo(newUser.getUsername());
        softAssertions.assertThat(createdUser.getEmail())
                .as("Email")
                .isEqualTo(newUser.getEmail());
        softAssertions.assertThat(createdUser.getFirstName())
                .as("First name")
                .isEqualTo(newUser.getFirstName());
        softAssertions.assertThat(createdUser.getLastName())
                .as("Last name")
                .isEqualTo(newUser.getLastName());
        softAssertions.assertAll();

    }

    @Test
    @DisplayName("Получение пользователя через API")
    @Description("Проверяет, что созданного пользователя можно получить по ID")
    void getUserTest() {
        User newUser = User.builder()
                .username(faker.name().username())
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .build();

        User createdUser = usersApi.createUser(newUser).as(User.class);
        createdUserId = createdUser.getId();

        Response response = usersApi.getUser(createdUserId);

        assertThat(response.statusCode()).isEqualTo(200);

        User fetchedUser = response.as(User.class);
        assertThat(fetchedUser.getId()).isEqualTo(createdUserId);
    }

    @Test
    @DisplayName("Удаление пользователя через API")
    @Description("Проверяет, что пользователь успешно удаляется")
    void deleteUserTest() {
        User newUser = User.builder()
                .username(faker.name().username())
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .build();

        User createdUser = usersApi.createUser(newUser).as(User.class);
        int userId = createdUser.getId();

        Response deleteResponse = usersApi.deleteUser(userId);
        assertThat(deleteResponse.statusCode()).isEqualTo(200);

        Response getResponse = usersApi.getUser(userId);
        assertThat(getResponse.statusCode()).isEqualTo(404);
    }

    @AfterEach
    void cleanup() {
        if (createdUserId != null) {
            usersApi.deleteUser(createdUserId);
        }
    }
}