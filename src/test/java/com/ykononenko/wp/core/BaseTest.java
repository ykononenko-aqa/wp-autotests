package com.ykononenko.wp.core;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.InputStream;
import java.util.Properties;

public abstract class BaseTest {

    protected static Properties config = new Properties();

    static {
        try (InputStream input = BaseTest.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {
            config.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    @BeforeEach
    void setUp() {
        // Настройка Selenide
        Configuration.baseUrl = config.getProperty("wp.base.url");
        Configuration.browser = config.getProperty("selenide.browser");
        Configuration.timeout = Long.parseLong(config.getProperty("selenide.timeout"));
        Configuration.headless = Boolean.parseBoolean(config.getProperty("selenide.headless"));
        Configuration.savePageSource = true;
        Configuration.screenshots = true;

        // Подключаем AllureSelenide для логирования шагов
        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(true));
    }

    @AfterEach
    void tearDown() {
        Selenide.closeWebDriver();
    }
}