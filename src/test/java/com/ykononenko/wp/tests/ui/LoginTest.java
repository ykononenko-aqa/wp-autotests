package com.ykononenko.wp.tests.ui;

import com.ykononenko.wp.core.BaseTest;
import com.ykononenko.wp.pages.LoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static org.assertj.core.api.Assertions.assertThat;

@Feature("Авторизация")
public class LoginTest extends BaseTest {

    private final LoginPage loginPage = new LoginPage();

    @Test
    @DisplayName("Успешный вход в админку WordPress")
    @Description("Проверяет, что пользователь может войти с корректными данными")
    void successfulLoginTest() {
        loginPage.openPage();
        String login = config.getProperty("wp.username");
        loginPage.loginAs(
                login,
                config.getProperty("wp.password")
        );

        $("#wpadminbar").shouldBe(visible);
        $("body").shouldHave(text("Привет, " + login));
    }

    @Test
    @DisplayName("Неуспешный вход с неверным паролем")
    @Description("Проверяет отображение ошибки при неверном пароле")
    void failedLoginTest() {
        loginPage.openPage();
        String login = config.getProperty("wp.username");
        loginPage.loginAs(login, "wrong_password");

        assertThat(loginPage.isLoginErrorDisplayed()).isTrue();
        assertThat(loginPage.getLoginErrorText())
                .contains("Ошибка: Введённый вами пароль пользователя " + login + " неверен. Забыли пароль?");
    }
}