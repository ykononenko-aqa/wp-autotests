package com.ykononenko.wp.pages.customhtml;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.io.File;

import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CheckBoxBugTestPage {
    private final SelenideElement container = $("#checkboxes-list");
    private final ElementsCollection serviceCheckBoxes = container.$$("input[type='checkbox']");
    private final SelenideElement amountPrice = $(".total-panel");

    @Step("Открыть страницу {pageLetter} теста чек-бокса")
    public void openPage(String pageLetter) {
        File file = new File("src/test/resources/test-buggy-page-" + pageLetter + ".html");
        String url = "file:///" + file.getAbsolutePath().replace("\\", "/");
        open(url);
    }

    @Step("Выбрать чек-бокс {value}")
    public CheckBoxBugTestPage chooseCheckBox(String value) {
        serviceCheckBoxes.findBy(value(value)).click();
        return this;
    }

    @Step("Получить весь список чек-боксов")
    public ElementsCollection getServiceCheckBoxes(){
        return serviceCheckBoxes;
    }

    public String getPriceText() {
        return amountPrice.getText();
    }
}
