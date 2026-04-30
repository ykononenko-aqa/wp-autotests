package com.ykononenko.wp.tests.hybrid;

import com.ykononenko.wp.api.UsersApi;
import com.ykononenko.wp.core.BaseTest;
import com.ykononenko.wp.model.User;
import com.ykononenko.wp.pages.LoginPage;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static org.assertj.core.api.Assertions.assertThat;

public class UserLoginHybridTest extends BaseTest {

    private UsersApi usersApi;
    private Faker faker;
    private Integer createdUserId;
    private LoginPage loginPage;


    @BeforeEach
    void initApi() {
        usersApi = new UsersApi(
                config.getProperty("wp.base.url"),
                config.getProperty("wp.username"),
                config.getProperty("wp.password")
        );
        faker = new Faker();
        loginPage = new LoginPage();
    }

    @Test
    @DisplayName("Создание пользователя через API")
    @Description("Проверяет, что пользователь успешно создается и возвращает корректные данные")
    void createUserTest() {
        String password = faker.internet().password();
        User newUser = User.builder()
                .username(faker.name().username())
                .email(faker.internet().emailAddress())
                .password(password)
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .build();

        Response response = usersApi.createUser(newUser);

        assertThat(response.statusCode()).isEqualTo(201);

        User createdUser = response.as(User.class);
        String login = createdUser.getUsername();
        createdUserId = createdUser.getId();

        loginPage.openPage();
        loginPage.loginAs(login, password);
        $("#wpadminbar").shouldBe(visible);
        $("body").shouldHave(text("Блог"));
    }


    @AfterEach
    void cleanup() {
        if (createdUserId != null) {
            usersApi.deleteUser(createdUserId);
        }
    }
}
