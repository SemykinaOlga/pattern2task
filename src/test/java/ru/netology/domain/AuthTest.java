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
    void shouldSuccessfulLoginValidUser() {
        RegistrationData registration = DataGenerator.Registration.getNewUser("active");
        SelenideElement form = $(".form");
        form.$("[data-test-id=login] input").setValue(registration.getLogin());
        form.$("[data-test-id=password] input").setValue(registration.getPassword());
        form.$(".button").click();
        form.$$(".heading").find(exactText("Личный кабинет")).shouldBe(exist);
    }

    @Test
    void shouldNotSuccessfulLoginBlockedUser() {
        RegistrationData registration = DataGenerator.Registration.getNewUser("blocked");
        SelenideElement form = $(".form");
        form.$("[data-test-id=login] input").setValue(registration.getLogin());
        form.$("[data-test-id=password] input").setValue(registration.getPassword());
        form.$(".button").click();
        form.$(".notification__content").shouldHave(exactText("Ошибка! Пользователь заблокирован"));
    }

    @Test
    void shouldReturnInvalidPassword() {
        RegistrationData registration = DataGenerator.Registration.getNewUser("active");
        SelenideElement form = $(".form");
        form.$("[data-test-id=login] input").setValue(registration.getLogin());
        form.$("[data-test-id=password] input").setValue(getBadPassword());
        form.$(".button").click();
        form.$(".notification__content").shouldHave(exactText("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldReturnInvalidLogin() {
        RegistrationData registration = DataGenerator.Registration.getNewUser("active");
        SelenideElement form = $(".form");
        form.$("[data-test-id=login] input").setValue(getBadLogin());
        form.$("[data-test-id=password] input").setValue(registration.getPassword());
        form.$(".button").click();
        form.$(".notification__content").shouldHave(exactText("Ошибка! Неверно указан логин или пароль"));
    }
}
