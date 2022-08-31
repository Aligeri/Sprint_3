package apiTest;

import api.*;
import io.qameta.allure.Description;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import responses.Responses;
import responses.CourierResponse;
import responses.LoginResponseId;
import util.Specifications;
import util.UtilClass;

import static io.restassured.RestAssured.given;
import static util.Error.*;
import static util.Link.*;

public class LoginCourierTest {

    @Test
    @DisplayName("Логин курьера. Успешная авторизация")
    @Description("Реализованы проверки:" +
            "\n1) курьер может авторизоваться;" +
            "\n2) успешный запрос возвращает id;")
    public void loginCourierSuccessTest() {
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
                Specifications.responseSpecification200());
        Login authorization = new Login(courier.getLogin(), courier.getPassword());
        LoginResponseId goodResponse1 = given()
                .body(authorization)
                .when()
                .post(COURIER_LOGIN)
                .then().log().all()
                .extract().as(LoginResponseId.class);
        Assert.assertNotNull(goodResponse1.getId());
        UtilClass.deleteCourierById(UtilClass.getCourierId(courier.getLogin(), courier.getPassword()));
    }

    @Test
    @DisplayName("Логин курьера. Проверка обязательности полей")
    @Description("Реализованы проверки:" +
            "\n1) для авторизации нужно передать все обязательные поля;" +
            "\n2) если какого-то поля нет, запрос возвращает ошибку;")
    public void loginCourierAllRequiredFieldsIsPresentTest() {
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
                Specifications.responseSpecification400());
        Login authorization = new Login(courier.getLogin(), "");
        Responses badResponse = given()
                .body(authorization)
                .when()
                .post(COURIER_LOGIN)
                .then().log().all()
                .extract().as(Responses.class);
        Assert.assertEquals(ERROR_LOGIN_400, badResponse.getMessage());
        UtilClass.deleteCourierById(UtilClass.getCourierId(courier.getLogin(), courier.getPassword()));
    }

    @Test
    @DisplayName("Логин курьера. Авторизация с невалидными данными")
    @Description("Реализованы проверки:" +
            "\n1) система вернёт ошибку, если неправильно указать логин или пароль;" +
            "\n2) если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;")
    public void loginCourierErrorOnInvalidFieldTest() {
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
                Specifications.responseSpecification404());
        Login authorization = new Login(courier.getLogin() + "1", courier.getPassword());
        Responses badResponse = given()
                .body(authorization)
                .when()
                .post(COURIER_LOGIN)
                .then().log().all()
                .extract().as(Responses.class);
        Assert.assertEquals(ERROR_LOGIN_409, badResponse.getMessage());
        UtilClass.deleteCourierById(UtilClass.getCourierId(courier.getLogin(), courier.getPassword()));
    }
}