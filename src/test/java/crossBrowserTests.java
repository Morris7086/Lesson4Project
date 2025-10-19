import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@TestMethodOrder(MethodOrderer.MethodName.class)
@DisplayName("Тестовый набор FourthLoginTests - проверка аутентификации")
public class crossBrowserTests {
    @BeforeAll
    static void before_all(TestInfo test_info) {
        System.out.println(test_info.getDisplayName() + " - начали выполнение.");
        Configuration.browser = System.getenv("browser");
//        Configuration.browser = "firefox"; // "chrome", "firefox", "edge"
    }

    @AfterAll
    static void after_all(TestInfo test_info) {
        System.out.println(test_info.getDisplayName() + " - закончили выполнение.");
    }

    @BeforeEach
    void before_each(TestInfo test_info) {
        System.out.println("Тест " + test_info.getDisplayName() + " - начали выполнение.");
        open("https://slqa.ru/cases/ChatGPTLogin/");
        //open("https://slqa.ru/cases/ChatGPTLogin/index_v02.html");
        //open("https://slqa.ru/cases/ChatGPTLogin/index_v03.html");
        //open("https://slqa.ru/cases/ChatGPTLogin/index_v04.html");
    }

    @AfterEach
    void after_each(TestInfo test_info) {
        System.out.println("Тест " + test_info.getDisplayName() + " - закончили выполнение.\n");
    }

    //01. Корректные логин и пароль - успешный вход в систему по нажатию кнопки "Login"
    //12. Проверить вход в систему под несколькими разными логинами
    //В тестовой модели удалить тест-кейс 12, перенести в тест-кейс 01.
    @ParameterizedTest (name = "01. Успешный вход в систему по кнопке Login под разными логинами, #{index}, username: {0}")
    @ValueSource (strings = {"standard_user", "problem_user", "performance_glitch_user", "error_user", "visual_user"})
    void test01_success_login_button(String username) {
        $("#username").sendKeys(username);
        $("#password").sendKeys("secret_sauce");
        $("#loginButton").click();
        $("#message").shouldHave(text("Вход в систему выполнен успешно! Загрузка..."));
        $("#message").shouldBe(visible);
        $("#message").shouldBe(cssClass("success"));
        $("#greeting").shouldHave(text("Welcome, " + username + "!"));
        $("#greeting").shouldBe(visible);
    }

    @Test
    @DisplayName("02. Корректный логин, пароль не соответствует логину - ошибка")
    void test02_error_wrong_password() {
        $("#username").sendKeys("standard_user");
        $("#password").sendKeys("wrong_password");
        $("#loginButton").click();
        $("#message").shouldHave(text("Invalid username or password."));
        $("#message").shouldBe(visible);
        $("#message").shouldBe(cssClass("error"));
        $("#greeting").shouldBe(empty);
        $("#greeting").shouldNotBe(visible);
    }

    @Test
    @DisplayName("03. Корректные логин и пароль - успешный вход в систему по нажатию клавиши Enter на клавиатуре")
    void test03_success_login_enter() {
        $("#username").sendKeys("standard_user");
        $("#password").sendKeys("secret_sauce");
        $("#password").pressEnter();
        $("#message").shouldHave(text("Вход в систему выполнен успешно! Загрузка..."));
        $("#message").shouldBe(/*Видимый*/ visible);
        $("#message").shouldBe(cssClass("success"));
        $("#greeting").shouldHave(text("Welcome, standard_user!"));
        $("#greeting").shouldBe(visible);
    }

    @Test
    @DisplayName("04. Выход из системы")
    void test04_logout_success() {
        $("#username").sendKeys("standard_user");
        $("#password").sendKeys("secret_sauce");
        $("#loginButton").click();
        $("#logoutButton").click();
        $("#logoutButton").shouldNotBe(visible);
        $("#loginContainer").shouldBe(visible);
        $("#loginInfo").shouldBe(visible);
        $("#username").shouldBe(visible);
        $("#password").shouldBe(visible);
        $("#loginButton").shouldBe(visible);
    }

    @Test
    @DisplayName("05. Некорректный логин, пароль от корректного логина - ошибка")
    void test05_wrong_login_correct_password() {
        $("#username").sendKeys("incorrect_login");
        $("#password").sendKeys("secret_sauce");
        $("#loginButton").click();
        $("#message").shouldHave(text("Invalid username or password."));
        $("#message").shouldBe(visible);
        $("#message").shouldBe(cssClass("error"));
        $("#greeting").shouldBe(empty);
        $("#greeting").shouldNotBe(visible);
    }
    //06. Корректный логин, пароль от другого корректного логина - ошибка
    //Эту проверку в текущей реализации невозможно реализовать,
    //т.к. у всех пользователей один и тот же пароль
    //Реализовано в версии 04 - user06/user06_password
    @Test
    @DisplayName("06. Корректный логин, пароль от другого корректного логина - ошибка")
    void test06_login_and_password_not_matched() {
        open("https://slqa.ru/cases/ChatGPTLogin/");
        $("#username").sendKeys("user06");
        $("#password").sendKeys("secret_sauce");
        $("#loginButton").click();
        $("#message").shouldHave(text("Invalid username or password."));
        $("#message").shouldBe(visible);
        $("#message").shouldBe(cssClass("error"));
        $("#greeting").shouldBe(empty);
        $("#greeting").shouldNotBe(visible);
    }

    @Test
    @DisplayName("07. Проверить, что под заблокированным пользователем нельзя войти в систему")
    void test07_error_blocked_user() {
        $("#username").sendKeys("locked_out_user");
        $("#password").sendKeys("secret_sauce");
        $("#loginButton").click();
        $("#message").shouldHave(text("Пользователь заблокирован."));
        $("#message").shouldBe(visible);
        $("#message").shouldBe(cssClass("error"));
        $("#greeting").shouldBe(empty);
        $("#greeting").shouldNotBe(visible);
    }

    @Test
    @DisplayName("08. Пустой логин, пароль от корректного логина")
    void test08_empty_login_correct_password() {
        $("#password").sendKeys("secret_sauce");
        $("#loginButton").click();
        $("#message").shouldHave(text("Username is required."));
        $("#message").shouldBe(visible);
        $("#message").shouldBe(cssClass("error"));
        $("#greeting").shouldBe(empty);
        $("#greeting").shouldNotBe(visible);
    }

    @Test
    @DisplayName("09. Пустой пароль, корректный логин")
    void test09_error_empty_password() {
        $("#username").sendKeys("standard_user");
        $("#loginButton").click();
        $("#message").shouldHave(text("Password is required."));
        $("#message").shouldBe(visible);
        $("#message").shouldBe(cssClass("error"));
        $("#greeting").shouldBe(empty);
        $("#greeting").shouldNotBe(visible);
    }

    @Test
    @DisplayName("10. Пустые логин и пароль")
    void test10_error_empty_login_and_password() {
        $("#loginButton").click();
        $("#message").shouldHave(text("Username and Password are required."));
        $("#message").shouldBe(visible);
        $("#message").shouldBe(cssClass("error"));
        $("#greeting").shouldBe(empty);
        $("#greeting").shouldNotBe(visible);
    }
    //11. Проверить, что при вводе пароль скрыт за звёздочками
    //Нет технической возможности автоматизировать эту проверку
    //По предложению Стаса - добавил проверку, но её недостаточно
    //В качестве примера - страница https://slqa.ru/cases/ChatGPTLogin/index_v02.html
    //На ней этот тест успешно проходит, но пароль не скрыт за звёздочками.
    //Все остальные тесты тоже успешно проходят на этой странице.
    //Требуется выполнять эту проверку ручным способом.
    @Test
    @DisplayName("11. Проверить, что при вводе пароль скрыт за звёздочками - проверка не полноценная, повторить вручную!!!")
    void test11_asterisks_in_password() {
        $("#password").type("secret_sauce");
        $("#password").shouldHave(attribute("type","password"));
    }
}
