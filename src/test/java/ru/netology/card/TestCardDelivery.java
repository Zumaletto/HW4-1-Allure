package ru.netology.card;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestCardDelivery {

    private DataGenerator dataGenerator = new DataGenerator();
    String city = dataGenerator.getCity();
    String name = dataGenerator.getName();
    String phone = dataGenerator.getPhone();

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setupClass() {
        open("http://localhost:9999/");
    }

    @Test
    @Order(1)
    void shouldSendValidValueAndChangeDate() {
        $("[data-test-id=city] .input__control").sendKeys(city);
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(dataGenerator.getDate(5));
        $("[data-test-id=name] .input__control").sendKeys(name);
        $("[data-test-id=phone] .input__control").sendKeys(phone);
        $("[data-test-id=agreement] .checkbox__box").click();
        $("[class=button__text]").click();
        $("[data-test-id=success-notification]").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=success-notification]  .notification__title")
                .shouldHave(exactText("Успешно!"));
        $("[data-test-id=success-notification]  .notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на " + dataGenerator.getDate(5)));

        $("[data-test-id=date] .input__control").doubleClick().sendKeys(dataGenerator.getDate(7));
        $("[class=button__text]").click();
        $("[data-test-id=replan-notification]").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=replan-notification] .notification__title")
                .shouldHave(exactText("Необходимо подтверждение"));
        $(byText("Перепланировать")).click();
        $("[data-test-id=success-notification]").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=success-notification]  .notification__title")
                .shouldHave(exactText("Успешно!"));
        $("[data-test-id=success-notification]  .notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на " + dataGenerator.getDate(7)));
    }

    @Test
    @Order(2)
    void shouldSendEmptyForm() {
        $("[data-test-id=agreement] .checkbox__box").click();
        $("[class=button__text]").click();
        $(withText("Поле обязательно для заполнения"))
                .shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    @Order(3)
    void shouldSendInvalidCity() {
        $("[data-test-id=city] .input__control").sendKeys("Petersburg");
        $("[class=button__text]").click();
        $("[data-test-id=city].input_invalid .input__sub")
                .shouldHave(Condition.exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    @Order(4)
    void shouldSendInvalidName() {

        $("[data-test-id=city] .input__control").sendKeys(city);
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(dataGenerator.getDate(5));
        $("[data-test-id=name] .input__control").sendKeys("Николай Илья Харитон Ульяна Яков");
        $("[data-test-id=phone] .input__control").sendKeys(phone);
        $("[data-test-id=agreement] .checkbox__box").click();
        $("[class=button__text]").click();
        $("[data-test-id=name].input_invalid .input__sub")
                .shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    @Order(5)
    void shouldSendValidNameWithЁ() {
        $("[data-test-id=city] .input__control").sendKeys(city);
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(dataGenerator.getDate(5));
        $("[data-test-id=name] .input__control").sendKeys("Лёвин Андрей");
        $("[data-test-id=phone] .input__control").sendKeys(phone);
        $("[data-test-id=agreement] .checkbox__box").click();
        $("[class=button__text]").click();
        $("[data-test-id=success-notification]").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=success-notification]  .notification__title")
                .shouldHave(exactText("Успешно!"));
        $("[data-test-id=success-notification]  .notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на " + dataGenerator.getDate(5)));
    }

    @Test
    @Order(6)
    void shouldSendInvalidPhoneNumber(){
        $("[data-test-id=city] .input__control").sendKeys(city);
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(dataGenerator.getDate(5));
        $("[data-test-id=name] .input__control").sendKeys(name);
        $("[data-test-id=phone] .input__control").sendKeys("+79321");
        $("[data-test-id=agreement] .checkbox__box").click();
        $("[class=button__text]").click();
        $("[data-test-id=phone].input_invalid .input__sub")
                .shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }
}
