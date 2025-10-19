package pageObjectTests.pageObject.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class RegistrationPage extends BasePage<RegistrationPage>{
    public static final SelenideElement PASSENGER_NAME = $("#passengerName");
    public static final SelenideElement PASSPORT_NUMBER = $("#passportNumber");
    public static final SelenideElement EMAIL = $("#email");
    public static final SelenideElement PHONE = $("#phone");
    public static final SelenideElement COMPLETE_REGISTRATION = $x("//button[text()='Завершить регистрацию']");
    public static final SelenideElement REGISTRATION_MESSAGE = $("#registrationMessage");

    public static final String REGISTRATION_MSG = "Регистрация успешно завершена!";


    @Step("Проверка автозаполнения данных пользователя")
    public RegistrationPage checkAutoFillings(String fullName, String passport, String email, String phone) {
        PASSENGER_NAME.shouldHave(value(fullName));
        PASSPORT_NUMBER.shouldHave(value(passport));
        EMAIL.shouldHave(value(email));
        PHONE.shouldHave(value(phone));

        return this;
    }
}
