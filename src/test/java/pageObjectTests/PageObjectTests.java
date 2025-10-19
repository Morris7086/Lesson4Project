package pageObjectTests;

import com.codeborne.selenide.ElementsCollection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class PageObjectTests {
    @BeforeEach
    void openPage() {
        open("https://slqa.ru/cases/DeepSeekFlights/");
    }

    @Test
    void wrongLogin() {
        $("#username").setValue("WrongUser");
        $("#password").setValue("WrongPass");
        $("#loginButton").click();

        $("#message").shouldHave(text("Неверное имя пользователя или пароль."));
    }

    @Test
    void pastDate() {
        $("#username").setValue("standard_user");
        $("#password").setValue("stand_pass1");
        $("#loginButton").click();

        $("#departureCity").click();
        $("[value='moscow']").click();
        $("#arrivalCity").click();
        $("[value='london']").click();
        $("#departureDate").clear();
        $x("//*[text()='Найти']").click();

        $("#searchMessage").shouldHave(text("Пожалуйста, укажите дату вылета."));
    }

    @Test
    void notFindFlights() {
        $("#username").setValue("standard_user");
        $("#password").setValue("stand_pass1");
        $("#loginButton").click();

        $("#departureCity").click();
        $("[value='kazan']").click();
        $("#arrivalCity").click();
        $("[value='paris']").click();
        $("#departureDate").setValue("2025-10-13");
        $x("//*[text()='Найти']").click();

        $("#flightsContainer").shouldHave(text("Рейсов по вашему запросу не найдено."));
    }

    @Test
    void wrongRegistration() {
        $("#username").setValue("standard_user");
        $("#password").setValue("stand_pass1");
        $("#loginButton").click();

        $("#departureCity").click();
        $("[value='moscow']").click();
        $("#arrivalCity").click();
        $("[value='london']").click();
        $("#departureDate").setValue("2025-10-14");
        $x("//*[text()='Найти']").click();

        $("#flightsContainer tr td button").click();
        $("#passengerName").setValue("Петров Петр Петрович");
        $("#passportNumber").setValue("LetterInNumAndSeries");
        $("#email").setValue("mail@mail.ru");
        $("#phone").setValue("123");
        $x("//*[text()='Завершить регистрацию']").click();

        $("#registrationMessage").shouldHave(text("Номер паспорта должен содержать только цифры и пробелы."));
    }

    @Test
    void successRegistration() {
        $("#username").setValue("standard_user");
        $("#password").setValue("stand_pass1");
        $("#loginButton").click();

        $("#departureCity").click();
        $("[value='moscow']").click();
        $("#arrivalCity").click();
        $("[value='london']").click();
        $("#departureDate").setValue("2025-10-14");
        $x("//*[text()='Найти']").click();

        $("#flightsContainer tr td button").click();
        $("#passengerName").setValue("Петров Петр Петрович");
        $("#passportNumber").setValue("1234123456");
        $("#email").setValue("mail@mail.ru");
        $("#phone").setValue("123");
        $x("//*[text()='Завершить регистрацию']").click();

        switchTo().alert().accept();

        $("#registrationMessage").shouldHave(text("Регистрация успешно завершена!"));

    }
}
