package com.ykononenko.wp.tests.customthml;

import com.ykononenko.wp.pages.customhtml.TestCheckBoxPage;
import com.ykononenko.wp.utils.TextParser;
import io.qameta.allure.Description;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CheckBoxTest {
    private final TextParser textParser = new TextParser();
    private final TestCheckBoxPage testCheckBoxPage = new TestCheckBoxPage();
    private final SoftAssertions softAssertions = new SoftAssertions();

    @Test
    @DisplayName("Тест для работы с чекбоксами")
    @Description("Проверяет, при выборе определенной кнопки заполняется подпись")
    void testRadio(){
        testCheckBoxPage.openPage();
        testCheckBoxPage.chooseCheckBox("testing", "design");
        testCheckBoxPage.verifyCheckBoxSelected("testing", "design");
        testCheckBoxPage.clickShowChosenButton();
        String text = testCheckBoxPage.getText();
        softAssertions.assertThat(textParser.parseCheckBoxChosenText(text,"Выбрано:")).containsExactlyInAnyOrderElementsOf(List.of("testing", "design"));
        softAssertions.assertAll();
    }
}
