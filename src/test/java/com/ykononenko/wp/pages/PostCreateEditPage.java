package com.ykononenko.wp.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.util.Objects;

import static com.codeborne.selenide.Selenide.*;

public class PostCreateEditPage {

    private final SelenideElement postTitle = $("#title");
    private final SelenideElement postPublishButton = $("#publish");
    private final SelenideElement viewPageLink = $("#wp-admin-bar-view");
    private final String contentFrameId = "content_ifr";
    private final String tinymceSelector = "#tinymce";


    @Step("Открыть страницу создания записи")
    public void openPage() {
        open("/wp-admin/post-new.php");
    }

    @Step("Ввести заголовок {title}")
    public PostCreateEditPage setTitle(String title) {
        postTitle.setValue(title);
        return this;
    }

    @Step("Ввести текст записи: {context}")
    public PostCreateEditPage setContext(String context) {
        switchTo().frame(contentFrameId);
        $(tinymceSelector).setValue(context);
        switchTo().defaultContent();
        return this;
    }

    @Step("Нажать кнопку Опубликовать")
    public void clickPublish() {
        postPublishButton.click();
    }

    @Step("Опубликовать запись с заголовком {title} и текстом {context}")
    public void publishPost(String title, String context) {
        setTitle(title)
                .setContext(context)
                .clickPublish();
    }

    @Step("Нажать на ссылку опубликованной записи")
    public void clickPublishedPostLink(){
        viewPageLink.click();
    }

    public int getPostId() {
        Wait().until(webDriver -> Objects.requireNonNull(webDriver.getCurrentUrl()).contains("post="));
        String url = webdriver().driver().url();
        return Integer.parseInt(url.replaceAll(".*post=(\\d+).*", "$1"));
    }
}
