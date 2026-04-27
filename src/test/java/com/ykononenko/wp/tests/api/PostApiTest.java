package com.ykononenko.wp.tests.api;

import com.ykononenko.wp.api.PostApi;
import com.ykononenko.wp.core.BaseTest;
import com.ykononenko.wp.model.Post;
import com.ykononenko.wp.model.PostStatus;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class PostApiTest extends BaseTest {
    private PostApi postApi;
    private Faker faker;
    private Integer createdPostId;
    private SoftAssertions softAssertions = new SoftAssertions();

    @BeforeEach
    void initApi() {
        postApi = new PostApi(
                config.getProperty("wp.base.url"),
                config.getProperty("wp.username"),
                config.getProperty("wp.password")
        );
        faker = new Faker();
    }

    @Test
    @DisplayName("Создание записи через API")
    @Description("Проверяет, что запись успешно создается и возвращает корректные данные")
    void createPost() {
        String title = faker.text().text(8);
        String content = faker.text().text();
        log.info("request title is {}", title);
        log.info("request content is {}", content);
        PostStatus status = PostStatus.PUBLISH;
        Post newPost = Post.builder()
                .title(new Post.RenderedProperty(title, null))
                .content(new Post.RenderedProperty(content, null))
                .status(status)
                .build();
        Response response = postApi.createPost(newPost);
        log.info("response status is {}", response.statusCode());
        log.info("response body is {}", response.getBody().asString());
        assertThat(response.statusCode()).isEqualTo(201);

        Post createdPost = response.as(Post.class);
        createdPostId = createdPost.getId();

        softAssertions.assertThat(createdPost.getId())
                .as("Post ID")
                .isNotNull();
        softAssertions.assertThat(createdPost.getTitle().getRaw())
                .as("Raw post title")
                .isEqualTo(title);
        softAssertions.assertThat(createdPost.getTitle().getRendered())
                .as("Render post title")
                .contains(title);
        softAssertions.assertThat(createdPost.getContent().getRaw())
                .as("Raw post content")
                .isEqualTo(content);
        softAssertions.assertThat(createdPost.getContent().getRendered())
                .as("Render post content")
                .contains(content);
        softAssertions.assertThat(createdPost.getStatus())
                .as("Post status")
                .isEqualTo(status);
        softAssertions.assertAll();
    }
}
