package pageObjectTests.pageObject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pageObjectTests.pageObject.pages.FlightsListPage;
import pageObjectTests.pageObject.pages.LoginPage;
import pageObjectTests.pageObject.pages.RegistrationPage;
import pageObjectTests.pageObject.pages.SearchPage;

import java.time.LocalDate;

import static pageObjectTests.pageObject.pages.FlightsListPage.getRegButton;
import static pageObjectTests.pageObject.pages.RegistrationPage.*;

public class RegistrationTests extends BaseTests {
    String fullName;
    String passport;
    String email;
    String phone;

    String departureCity;
    String arrivalCity;
    LocalDate departureDate;

    @BeforeEach
    void prepareForTest() {
        // Логин пользователя
        fullName = "Иванов Иван Иванович";
        passport = "1234 567890";
        email = "ivanov@example.com";
        phone = "+7 (123) 456-7890";
        String username = "standard_user";
        String password = "stand_pass1";
        new LoginPage().login(username, password);

        // Поиск рейса
        departureCity = "Москва";
        arrivalCity = "Нью-Йорк";
        departureDate = LocalDate.now();
        new SearchPage().searchFlights(departureCity, arrivalCity, departureDate);

        // Инициация регистрации
        new FlightsListPage().pushTheButton(getRegButton());
    }

    @Test
    @DisplayName("Проверить автозаполнение данных пользователя")
    void checkAutoFillingsUserData() {
        new RegistrationPage().checkAutoFillings(fullName, passport, email, phone);
    }

    @Test
    @DisplayName("Успешная регистрация")
    void successRegistration() {
        new RegistrationPage()
                .pushTheButton(COMPLETE_REGISTRATION)
                .checkMsg(REGISTRATION_MESSAGE, REGISTRATION_MSG);
    }
}
