package pageObjectTests.pageObject.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlightsListPage extends BasePage<FlightsListPage> {
    public static final ElementsCollection THEAD_FLIGHT_TABLE = $("#flightsTable thead tr").$$("th");
    public static final ElementsCollection FLIGHTS_CONTAINER = $$("#flightsContainer tr");

    List<String> headers = THEAD_FLIGHT_TABLE.texts();
    int departure= headers.indexOf("Отправление");
    int arrival = headers.indexOf("Прибытие");
    int date = headers.indexOf("Дата");

    @Step("Проверить отображение найденных рейсов")
    public FlightsListPage checkFlights(String departureCity, String arrivalCity, String departureDate) {
        List<String> flightsData = FLIGHTS_CONTAINER.first().$$("td").texts();
        assertEquals(departureCity, flightsData.get(departure));
        assertEquals(arrivalCity, flightsData.get(arrival));
        assertEquals(departureDate, flightsData.get(date));

        return this;
    }

    public static SelenideElement getRegButton() {
        return FLIGHTS_CONTAINER.first().$("button");
    }
}
