package pageObjectTests.pageObject.pages;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebElementCondition;
import io.qameta.allure.Step;

import java.time.LocalDate;

import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class SearchPage extends BasePage<SearchPage> {
    public static final SelenideElement SEARCH_FORM = $("#flightForm");
    public static final SelenideElement GREETING = $("#greeting");
    public static final SelenideElement LOGOUT_BUTTON = $("#logoutButton");
    public static final SelenideElement DEPARTURE_CITY = $("#departureCity");
    public static final SelenideElement ARRIVAL_CITY = $("#arrivalCity");
    public static final SelenideElement DEPARTURE_DATE = $("#departureDate");
    public static final SelenideElement FIND_BUTTON = $x("//button[text()='Найти']");

    @Step("Поиск рейсов")
    public SearchPage searchFlights(String departureCity, String arrivalCity, LocalDate departureDate) {
        selectFromDropdownList(DEPARTURE_CITY, departureCity);
        selectFromDropdownList(ARRIVAL_CITY, arrivalCity);
        fillField(DEPARTURE_DATE, departureDate.format(FORMATTER_TO_STRING));
        pushTheButton(FIND_BUTTON);

        return this;
    }

    @Step("Проверка найденных рейсов")
    public SearchPage checkVisibleSearchForm(boolean isVisible) {
        WebElementCondition condition = isVisible ? visible : hidden;
        SEARCH_FORM.shouldBe(condition);

        return this;
    }
}
