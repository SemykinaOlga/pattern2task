package ru.netology.domain;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private DataGenerator() {
    }

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static class Registration {
        private Registration() {
        }

        public static RegistrationData getNewUser(String status) {
            Faker faker = new Faker(new Locale("ru"));

            RegistrationData registration = new RegistrationData(faker.name().firstName(),
                    faker.internet().password(), status);
            given()
                    .spec(requestSpec)
                    .body(registration)
                    .when()
                    .post("/api/system/users")
                    .then()
                    .statusCode(200);
            return registration;
        }
    }
}

