package pageObjectTests.pageObject.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;

public class BasePage<T> {
    public static final DateTimeFormatter FORMATTER_TO_STRING = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Step("Проверка сообщения")
    public T checkMsg(SelenideElement selenideElement, String msg) {
        selenideElement.shouldHave(text(msg));

        return (T) this;
    }

    @Step("Заполение поля")
    public T fillField(SelenideElement field, String value) {
        field.setValue(value);

        return (T) this;
    }

    @Step("Выбор из списка")
    public T selectFromDropdownList(SelenideElement dropdownList, String value) {
        dropdownList.selectOption(value);

        return (T) this;
    }

    @Step("Нажатие кнопки")
    public T pushTheButton(SelenideElement button) {
        button.click();

        return (T) this;
    }


}
