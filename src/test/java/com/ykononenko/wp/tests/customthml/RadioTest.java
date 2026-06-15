package com.ykononenko.wp.tests.customthml;

import com.ykononenko.wp.pages.customhtml.TestRadioPage;
import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RadioTest {

    private final TestRadioPage testRadioPage = new TestRadioPage();

    @Test
    @DisplayName("Тест для работы с радиокнопками")
    @Description("Проверяет, при выборе определенной кнопки заполняется подпись")
    void testRadio(){
        testRadioPage.openPage();
        testRadioPage.chooseRadio("green");
        testRadioPage.verifyRadioSelected("green");
        testRadioPage.clickShowChosenButton();
        String text = testRadioPage.getText();
        assertThat(text).isEqualTo("Выбрано: green");
    }
}
