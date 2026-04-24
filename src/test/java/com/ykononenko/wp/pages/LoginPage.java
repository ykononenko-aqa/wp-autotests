package com.ykononenko.wp.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginPage {

    private final SelenideElement usernameInput = $("#user_login");
    private final SelenideElement passwordInput = $("#user_pass");
    private final SelenideElement loginButton = $("#wp-submit");
    private final SelenideElement loginError = $("#login_error");

    @Step("Открыть страницу логина")
    public void openPage() {
        open("/wp-admin");
    }

    @Step("Ввести логин: {username}")
    public LoginPage enterUsername(String username) {
        usernameInput.setValue(username);
        return this;
    }

    @Step("Ввести пароль")
    public LoginPage enterPassword(String password) {
        passwordInput.setValue(password);
        return this;
    }

    @Step("Нажать кнопку Login")
    public void clickLogin() {
        loginButton.click();
    }

    @Step("Залогиниться как {username}")
    public void loginAs(String username, String password) {
        enterUsername(username).
                enterPassword(password).
                clickLogin();
    }

    public boolean isLoginErrorDisplayed() {
        return loginError.isDisplayed();
    }

    public String getLoginErrorText() {
        return loginError.getText();
    }
}

