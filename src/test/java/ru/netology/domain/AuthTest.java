package ru.netology.domain;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.*;

public class AuthTest {

    @Test
    void ShouldStatus200() {
        RegistrationData registration = DataGenerator.Registration.getNewUser("active");
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        $("[data-test-id=login] input").setValue(registration.getLogin());
        $("[data-test-id=password] input").setValue(registration.getPassword());
        $(".button").click();
        $$(".heading").find(exactText("Личный кабинет")).shouldBe(exist);
    }

    @Test
    void ShouldStatus400() {
        RegistrationData registration = DataGenerator.Registration.getNewUser("blocked");
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        $("[data-test-id=login] input").setValue(registration.getLogin());
        $("[data-test-id=password] input").setValue(registration.getPassword());
        $(".button").click();
        $(".notification__content").shouldHave(exactText("Ошибка! Пользователь заблокирован"));
    }

    @Test
    void ShouldReturnInvalidPassword() {
        RegistrationData registration = DataGenerator.Registration.getNewUser("active");
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        $("[data-test-id=login] input").setValue(registration.getLogin());
        $("[data-test-id=password] input").setValue("123");
        $(".button").click();
        $(".notification__content").shouldHave(exactText("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void ShouldReturnInvalidLogin() {
        RegistrationData registration = DataGenerator.Registration.getNewUser("active");
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        $("[data-test-id=login] input").setValue("123");
        $("[data-test-id=password] input").setValue(registration.getPassword());
        $(".button").click();
        $(".notification__content").shouldHave(exactText("Ошибка! Неверно указан логин или пароль"));
    }
}
