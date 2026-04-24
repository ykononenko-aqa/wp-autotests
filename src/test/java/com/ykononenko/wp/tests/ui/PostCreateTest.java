package com.ykononenko.wp.tests.ui;

import com.ykononenko.wp.api.PostApi;
import com.ykononenko.wp.core.BaseTest;
import com.ykononenko.wp.pages.LoginPage;
import com.ykononenko.wp.pages.PostCreateEditPage;
import io.qameta.allure.Description;
import net.datafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class PostCreateTest extends BaseTest {
    private final PostCreateEditPage postCreateEditPage = new PostCreateEditPage();
    private final LoginPage loginPage = new LoginPage();
    private PostApi postApi;
    private Faker faker;
    private int postId;


    @BeforeEach
    void init() {
        faker = new Faker();
        postApi = new PostApi(
                config.getProperty("wp.base.url"),
                config.getProperty("wp.username"),
                config.getProperty("wp.password")
        );
    }

    @Test
    @DisplayName("Создание записи")
    @Description("Проверяем, что можно создать запись")
    void successfulCreatePostTest() {
        loginPage.openPage();
        loginPage.loginAs(config.getProperty("wp.username"), config.getProperty("wp.password"));
        postCreateEditPage.openPage();
        String title = faker.text().text(8);
        String context = faker.text().text();
        postCreateEditPage.publishPost(title, context);
        postId = postCreateEditPage.getPostId();
        postCreateEditPage.clickPublishedPostLink();
        $("h1.wp-block-post-title").shouldHave(text(title));
        $(".entry-content").shouldHave(text(context));
    }


    @AfterEach
    void cleanup() {
        postApi.deletePost(postId);
    }
}
