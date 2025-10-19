import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CinemaTestsV2 {
    private static final SelenideElement AGE = $("[name='age']");
    private static final SelenideElement DATE = $("[name='date']");
    // Список доступных для выбора дат
    private static final List<LocalDate> AVAILABLE_DATES =
            Stream.iterate(LocalDate.now().plusDays(1), date -> date.plusDays(1))
            .limit(7)
            .toList();
    private static final SelenideElement SUBMIT_BUTTON = $("[type='submit']");
    private static final SelenideElement TICKET_PRICE = $("div");
    // Мапа: Начало сеанса - value элемента в DOM
    private static final Map<String, Integer> SESSION = Map.of(
            "09:30", 1,
            "11:00", 2,
            "12:30", 3,
            "14:00", 4,
            "16:00", 5,
            "17:30", 6,
            "19:00", 7,
            "20:30", 8,
            "22:00", 9,
            "23:30", 10
    );

    @BeforeEach
    void prepareBeforeTest() {
        open("http://92.51.36.108:7777/sl.qa/cinema/index.php");
    }

    @Test
    @DisplayName("Проверка ограничений ввода возраста")
    void checkAgeRange() {
       AGE.shouldHave(
               attribute("type", "number"),
               attribute("min", "0"),
               attribute("max", "99")
       );
    }

    @Test
    @DisplayName("Проверка ограничений ввода даты")
    void checkDateRange() {
        DATE.shouldHave(
                attribute("type", "date"),
                attribute("min", AVAILABLE_DATES.getFirst().toString()),
                attribute("max", AVAILABLE_DATES.getLast().toString())
        );
    }

    @Test
    @DisplayName("Проверка сообщения о незаполненных полях")
    void checkEmptyFieldsMsg() {
        SUBMIT_BUTTON.click();

        TICKET_PRICE.shouldHave(
                text("*надо указать возраст для расчёта стоимости билета*"),
                text("*надо указать фильм для расчёта стоимости билета*"),
                text("*надо указать дату сеанса для расчёта стоимости билета*"),
                text("*надо указать сеанс для расчёта стоимости билета*")
        );
    }

    @ParameterizedTest
    @CsvSource({"12", "18"})
    @DisplayName("Проверка возростного ограничения")
    void checkAgeRestriction(int ageCategory) {
        AGE.setValue(String.valueOf(ageCategory - 1));
        DATE.setValue(AVAILABLE_DATES.getFirst().toString());
        session("09:30").click();
        film(ageCategory).click();
        SUBMIT_BUTTON.click();

        TICKET_PRICE.shouldHave(text("*Этот фильм можно смотреть только с " + ageCategory + " лет*"));
    }

    @ParameterizedTest
    @CsvSource({"5, 5, 12:30", "6, 6, 14:00"})
    @DisplayName("Проверка суммарной скидки")
    void checkSumDiscount(int age, int dayOfWeek, String session) {
        AGE.setValue(String.valueOf(age));
        DATE.setValue(getDateDayOfWeek(dayOfWeek));
        session(session).click();
        film(0).click();
        SUBMIT_BUTTON.click();

        TICKET_PRICE.shouldHave(text(500 - getDiscountByAge(age) - getDiscountByDayOfWeek(dayOfWeek) - getDiscountBySession(session) + " рублей"));
    }

    /**
     *
     * @param time время сеанса
     * @return локатор элемента по времени сеанса
     */
    private static SelenideElement session(String time) {
        return $( "[name='session'][value='" + SESSION.get(time) + "']");
    }

    /**
     *
     * @param ageCategory категоря возраста (0, 12, 18)
     * @return локатор случайного фильма по категории возраста
     */
    private static SelenideElement film(int ageCategory) {
        Map<Integer, List<String>> FILMS = Map.of(
                0, List.of("king"),
                12, List.of("back"),
                18, List.of("crime", "killers", "tango")
        );

        List<String> filmList = FILMS.get(ageCategory);
        String film = filmList.get(new Random().nextInt(filmList.size()));

        return $("[name='film'][value='" + film + "']");
    }

    /**
     *
     * @param dayOfWeek порядковый номер дня недели, начиная с понедельника
     * @return возвращает дату выбранного дня недели
     */
    private String getDateDayOfWeek(int dayOfWeek) {
        return AVAILABLE_DATES.stream()
                .filter(date -> date.getDayOfWeek().getValue() == dayOfWeek)
                .findFirst()
                .toString();
    }

    private int getDiscountByAge(int age) {
        return age < 6 ? 250 : 0;
    }

    private int getDiscountBySession(String session) {
        return SESSION.get(session) < 4 ? 50 : 0;
    }

    private int getDiscountByDayOfWeek(int dayOfWeek) {
        return dayOfWeek < 6 ? 100 : 0;
    }
}
