package pageObjectTests.pageObject.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage extends BasePage<LoginPage> {
    public static final SelenideElement USERNAME = $("#username");
    public static final SelenideElement PASSWORD = $("#password");
    public static final SelenideElement LOGIN_BUTTON = $("#loginButton");
    public static final SelenideElement LOGIN_MESSAGE = $("#message");

    public static final String SUCCESS_LOGIN_MSG = "Вход в систему выполнен успешно";
    public static final String WRONG_LOGIN_MSG = "Неверное имя пользователя или пароль";
    public static final String REQUIRED_CREDENTIALS_MSG = "Username and Password are required";
    public static final String LOCKED_USER_MSG = "Пользователь заблокирован.";

    @Step("Вход в систему")
    public LoginPage login(String username, String password) {
        USERNAME.setValue(username);
        PASSWORD.setValue(password);
        LOGIN_BUTTON.click();

        return this;
    }
}
