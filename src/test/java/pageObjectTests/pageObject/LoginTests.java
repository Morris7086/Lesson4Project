package pageObjectTests.pageObject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pageObjectTests.pageObject.pages.LoginPage;

import static pageObjectTests.pageObject.pages.LoginPage.*;

public class LoginTests extends BaseTests {

    // Посчитал, что проверка данных пользователя в шапке - это уже проверка на странице поиска авиабилетов,
    // т.к. элемент находится на ней. Проверка реализована в наборе тестов на страницу посика авиабилетов
    @Test
    @DisplayName("1.1 Успешный логин")
    void successLogin() {
        new LoginPage()
                .login("standard_user", "stand_pass1")
                .checkMsg(LOGIN_MESSAGE, SUCCESS_LOGIN_MSG);
    }

    @Test
    @DisplayName("1.2 Неуспешный логин")
    void wrongLogin() {
        new LoginPage()
                .login("standard_user", "wrong_stand_pass1")
                .checkMsg(LOGIN_MESSAGE, WRONG_LOGIN_MSG);
    }

    @Test
    @DisplayName("1.3 Логин с пустыми полями")
    void emptyFieldsLogin() {
        new LoginPage()
                .pushTheButton(LOGIN_BUTTON)
                .checkMsg(LOGIN_MESSAGE, REQUIRED_CREDENTIALS_MSG);
    }

    @Test
    @DisplayName("1.4 Вход заблокированного пользователя")
    void lockedUserLogin() {
        new LoginPage()
                .login("locked_out_user", "lock_pass2")
                .checkMsg(LOGIN_MESSAGE, LOCKED_USER_MSG);
    }
}
