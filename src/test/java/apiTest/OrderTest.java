package apiTest;

import api.*;
import io.qameta.allure.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import responses.OrderListResponse;
import util.Specifications;
import util.UtilClass;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static util.Link.*;

public class OrderTest {

    public static Stream<Arguments> orderColor() {
        return Stream.of(
                Arguments.of((Object) new String[]{"BLACK"}),
                Arguments.of((Object) new String[]{"GREY"}),
                Arguments.of((Object) new String[]{"BLACK", "GREY"}),
                Arguments.of((Object) new String[]{null}));
    }

    @ParameterizedTest
    @MethodSource("orderColor")
    @DisplayName("Создание заказа. Успешное создание заказа")
    @Description("Реализованы проверки:" +
            "\n1) можно указать один из цветов — BLACK или GREY;" +
            "\n2) можно указать оба цвета;" +
            "\n3) можно совсем не указывать цвет;" +
            "\n4) тело ответа содержит track;")
    public void createOrderSuccessTest(String[] color) {
        Specifications.installSpecification(Specifications.requestSpecification(BASE_URI),
                Specifications.responseSpecification201());
        Order order = UtilClass.getOrder(color);
        OrderListResponse goodResponse = given()
                .body(order).log().all()
                .when()
                .post(ORDER_CREATION)
                .then()
                .extract().as(OrderListResponse.class);
        Assertions.assertNotNull(goodResponse.getTrack());
        UtilClass.deleteOrderById(UtilClass.getOrderId(order.getFirstName(), order.getLastName(), order.getAddress(),
                order.getMetroStation(), order.getPhone(), order.getRentTime(), order.getDeliveryDate(),
                order.getComment(), order.getColor()));
    }
}