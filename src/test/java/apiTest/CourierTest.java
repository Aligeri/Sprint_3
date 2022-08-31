package apiTest;

import api.*;
import io.qameta.allure.Description;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import responses.Responses;
import responses.CourierResponse;
import util.UtilClass;
import util.Specifications;

import java.lang.reflect.Type;

import static io.restassured.RestAssured.given;
import static util.Link.*;
import static util.Error.*;

public class CourierTest {

    @Test
    @DisplayName("Создание курьера. Успешное создание курьера")
    @Description("Реализованы проверки:" +
            "\n1) курьера можно создать;" +
            "\n2) запрос возвращает правильный код ответа;" +
            "\n3) успешный запрос возвращает ok: true;")
    public void addCourierSuccessTest() {
        Specifications.installSpecification(Specifications.requestSpecification(BASE_URI),
                Specifications.responseSpecification201());
        Courier courier = UtilClass.getCourier();
        CourierResponse response = given()
                .body(courier)
                .when()
                .post(COURIER_CREATION)
                .then().log().all()
                .extract().as(CourierResponse.class);
        Assert.assertTrue(response.isOk());
        UtilClass.deleteCourierById(UtilClass.getCourierId(courier.getLogin(), courier.getPassword()));
    }

    @Test
    @DisplayName("Создание курьера. Дублирование курьера")
    @Description("Реализованы проверки:" + "\n1) нельзя создать двух одинаковых курьеров" +
            "\n2) если создать пользователя с логином, который уже есть, возвращается ошибка")
    public void addCourierDuplicateTest() {
        Specifications.installSpecification(Specifications.requestSpecification(BASE_URI),
                Specifications.responseSpecification201());
        Courier courier = UtilClass.getCourier();
        CourierResponse goodResponse = given()
                .body(courier)
                .when()
                .post(COURIER_CREATION)
                .then().log().all()
                .extract().as(CourierResponse.class);
        Assert.assertTrue(goodResponse.isOk());

        Specifications.installSpecification(Specifications.requestSpecification(BASE_URI),
                Specifications.responseSpecification409());
        Courier courier1 = new Courier(courier.getLogin(), courier.getPassword(), courier.getFirstName());
        Responses badResponse = given()
                .body(courier1)
                .when()
                .post(COURIER_CREATION)
                .then().log().all()
                .extract().as((Type) Responses.class);
        Assert.assertEquals(ERROR_COURIER_409, badResponse.getMessage());
        UtilClass.deleteCourierById(UtilClass.getCourierId(courier.getLogin(), courier.getPassword()));
    }

    @Test
    @DisplayName("Создание курьера. Проверка обязательности полей")
    @Description("Реализованы проверки:" +
            "\n1) чтобы создать курьера, нужно передать в ручку все обязательные поля;" +
            "\n2) если одного из полей нет, запрос возвращает ошибку;")
    public void addCourierAllRequiredFieldsIsPresentTest() {
        Specifications.installSpecification(Specifications.requestSpecification(BASE_URI),
                Specifications.responseSpecification400());
        Courier courier = UtilClass.getCourierWithoutLogin();
        Responses badResponse = given()
                .body(courier)
                .when()
                .post(COURIER_CREATION)
                .then().log().all()
                .extract().as(Responses.class);
        Assert.assertEquals(ERROR_COURIER_400, badResponse.getMessage());
    }
}