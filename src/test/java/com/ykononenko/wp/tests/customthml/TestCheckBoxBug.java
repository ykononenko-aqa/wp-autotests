package com.ykononenko.wp.tests.customthml;

import com.codeborne.selenide.ElementsCollection;
import com.ykononenko.wp.pages.customhtml.CheckBoxBugTestPage;
import com.ykononenko.wp.utils.TextParser;
import io.qameta.allure.Description;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@Slf4j
public class TestCheckBoxBug {
    private final TextParser textParser = new TextParser();
    private final CheckBoxBugTestPage checkBoxBugTestPage = new CheckBoxBugTestPage();

    @ParameterizedTest
    @CsvSource({"A", "B", "C"})
    @DisplayName("Проверка суммы на странице с багом")
    @Description("Ищет баг на уменьшение суммы при выборе набора услуг")
    void testBug(String pageLetter) {
        checkBoxBugTestPage.openPage(pageLetter);
        ElementsCollection checkBoxCollection = checkBoxBugTestPage.getServiceCheckBoxes();
        checkBoxCollection.forEach(selenideElement -> {
            String price = checkBoxBugTestPage.getPriceText();
            log.info("price text = {}", price);
            int amountPrice = textParser.getAmountPrice(checkBoxBugTestPage.getPriceText());
            int oldPrice = amountPrice;
            String value = selenideElement.getValue();
            log.info("choose check-box {}", value);
            checkBoxBugTestPage.chooseCheckBox(value);
            amountPrice = textParser.getAmountPrice(checkBoxBugTestPage.getPriceText());
            if (oldPrice > amountPrice){
                log.error("Error on selecting checkBox {}", value);
                Assertions.fail("Total decreased after selecting " + value);
            }
        });
    }
}
