package com.ykononenko.wp.pages.customhtml;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.io.File;

import static com.codeborne.selenide.Condition.selected;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selenide.*;

public class TestRadioPage {
    private final SelenideElement colorRadio = $(By.name("color"));
    private final ElementsCollection colorRadios = $$(By.name("color"));
    private final SelenideElement showChosenButton = $(By.id("submitBtn"));
    private final SelenideElement chosenText = $(By.id("result"));

    @Step("Открыть страницу теста радиокнопок")
    public void openPage() {
        File file = new File("src/test/resources/test-radios.html");
        String url = "file:///" + file.getAbsolutePath().replace("\\", "/");
        open(url);
    }

    @Step("Выбрать цвет {radioValue}")
    public TestRadioPage chooseRadio(String radioValue) {
        colorRadio.selectRadio(radioValue);  // показываем selectRadio
        return this;
    }

    @Step("Нажать \"Показать выбранный\"")
    public TestRadioPage clickShowChosenButton(){
        showChosenButton.click();
        return this;
    }

    @Step("Проверить, что выбран цвет {expectedValue}")
    public TestRadioPage verifyRadioSelected(String expectedValue) {
        colorRadios.findBy(value(expectedValue)).shouldBe(selected);
        return this;
    }

    public String getText(){
        return chosenText.getText();
    }
}
