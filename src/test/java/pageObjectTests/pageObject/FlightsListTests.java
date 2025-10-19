package pageObjectTests.pageObject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pageObjectTests.pageObject.pages.FlightsListPage;
import pageObjectTests.pageObject.pages.LoginPage;
import pageObjectTests.pageObject.pages.SearchPage;

import java.time.LocalDate;

import static pageObjectTests.pageObject.pages.BasePage.FORMATTER_TO_STRING;
import static pageObjectTests.pageObject.pages.SearchPage.*;
import static pageObjectTests.pageObject.pages.SearchPage.FIND_BUTTON;

public class FlightsListTests extends BaseTests {
    String departureCity;
    String arrivalCity;
    LocalDate departureDate;

    @BeforeEach
    void prepareForTest() {
        String username = "standard_user";
        String password = "stand_pass1";
        new LoginPage().login(username, password);

        departureCity = "Москва";
        arrivalCity = "Нью-Йорк";
        departureDate = LocalDate.now();
        new SearchPage()
                .selectFromDropdownList(DEPARTURE_CITY, departureCity)
                .selectFromDropdownList(ARRIVAL_CITY, arrivalCity)
                .fillField(DEPARTURE_DATE, departureDate.format(FORMATTER_TO_STRING))
                .pushTheButton(FIND_BUTTON);
    }

    @Test
    @DisplayName("Проверка отображения найденных вылетов")
    void checkDisplayFoundFlights() {
        new FlightsListPage()
                .checkFlights(departureCity, arrivalCity, departureDate.toString());
    }
}
