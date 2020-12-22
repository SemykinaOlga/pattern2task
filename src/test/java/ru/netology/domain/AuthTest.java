package ru.netology.domain;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.domain.DataGenerator.Registration.*;

public class AuthTest {
    @BeforeEach
    void openURL() {
        open("http://localhost:9999");
    }

    @Test
    void ShouldStatus200() {
        RegistrationData registration = DataGenerator.Registration.getNewUser("active");
        SelenideElement form = $(".form");
        $("[data-test-id=login] input").setValue(registration.getLogin());
        $("[data-test-id=password] input").setValue(registration.getPassword());
        $(".button").click();
        $$(".heading").find(exactText("Личный кабинет")).shouldBe(exist);
    }

    @Test
    void ShouldStatus400() {
        RegistrationData registration = DataGenerator.Registration.getNewUser("blocked");
        SelenideElement form = $(".form");
        $("[data-test-id=login] input").setValue(registration.getLogin());
        $("[data-test-id=password] input").setValue(registration.getPassword());
        $(".button").click();
        $(".notification__content").shouldHave(exactText("Ошибка! Пользователь заблокирован"));
    }

    @Test
    void ShouldReturnInvalidPassword() {
        RegistrationData registration = DataGenerator.Registration.getNewUser("active");
        SelenideElement form = $(".form");
        $("[data-test-id=login] input").setValue(registration.getLogin());
        $("[data-test-id=password] input").setValue(getBadPassword());
        $(".button").click();
        $(".notification__content").shouldHave(exactText("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void ShouldReturnInvalidLogin() {
        RegistrationData registration = DataGenerator.Registration.getNewUser("active");
        SelenideElement form = $(".form");
        $("[data-test-id=login] input").setValue(getBadLogin());
        $("[data-test-id=password] input").setValue(registration.getPassword());
        $(".button").click();
        $(".notification__content").shouldHave(exactText("Ошибка! Неверно указан логин или пароль"));
    }
}
