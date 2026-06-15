package com.ykononenko.wp.pages.customhtml;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.io.File;

import static com.codeborne.selenide.Condition.selected;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selenide.*;

public class TestCheckBoxPage {
    private final ElementsCollection interestCheckBoxes = $$(By.name("interests"));
    private final SelenideElement showChosenButton = $(By.id("showSelectedBtn"));
    private final SelenideElement chosenText = $(By.id("result"));

    @Step("Открыть страницу теста чек-бокса")
    public void openPage() {
        File file = new File("src/test/resources/test-checkbox.html");
        String url = "file:///" + file.getAbsolutePath().replace("\\", "/");
        open(url);
    }

    @Step("Выбрать чекбоксы: {values}")
    public TestCheckBoxPage chooseCheckBox(String... values) {
        for (String value : values) {
            interestCheckBoxes.findBy(value(value)).click();
        }
        return this;
    }

    @Step("Нажать \"Показать выбранные\"")
    public TestCheckBoxPage clickShowChosenButton(){
        showChosenButton.click();
        return this;
    }

    @Step("Проверить, что выбраны чекбоксы: {values}")
    public TestCheckBoxPage verifyCheckBoxSelected(String... values) {
        for (String value : values) {
            interestCheckBoxes.findBy(value(value)).shouldBe(selected);
        }
        return this;
    }

    public String getText(){
        return chosenText.getText();
    }
}
