package pageObjectTests.pageObject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pageObjectTests.pageObject.pages.LoginPage;
import pageObjectTests.pageObject.pages.SearchPage;

import java.time.LocalDate;

import static com.codeborne.selenide.Condition.visible;
import static pageObjectTests.pageObject.pages.LoginPage.LOGIN_BUTTON;
import static pageObjectTests.pageObject.pages.SearchPage.*;

public class SearchTests extends BaseTests {
    String fullName;

    @BeforeEach
    void prepareForTest() {
        String username = "standard_user";
        String password = "stand_pass1";
        fullName = "Иванов Иван Иванович";
        new LoginPage().login(username, password);
    }

    @Test
    @DisplayName("Oтображение данных пользователя")
    void displayUserData() {
        new SearchPage().checkMsg(GREETING, "Добро пожаловать, " + fullName + "!");
    }

    @Test
    @DisplayName("Выход из системы")
    void logout() {
        new SearchPage().pushTheButton(LOGOUT_BUTTON);
        LOGIN_BUTTON.shouldBe(visible);
    }

    // Как действовать на границе страниц? С одной стороны заполнение полей и нажатие кнопки,
    // с другой - проверка результата на следующей странице
    @Test
    @DisplayName("2.1 Проверка поиска с валидными данными")
    void successSearch() {
        new SearchPage()
                .searchFlights("Москва", "Нью-Йорк", LocalDate.now())
                .checkVisibleSearchForm(false);
    }

}
