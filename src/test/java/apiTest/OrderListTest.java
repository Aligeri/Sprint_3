package apiTest;

import org.junit.jupiter.api.DisplayName;
import responses.OrderListResponse;
import util.Specifications;
import io.qameta.allure.Description;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static util.Link.*;

public class OrderListTest {

    @Test
    @DisplayName("Список заказов. Получение списка заказов")
    @Description("Реализованы проверки:" +
            "\n1) в тело ответа возвращается список заказов;")
    public void getOrderSuccessTest() {
        Specifications.installSpecification(Specifications.requestSpecification(BASE_URI),
                Specifications.responseSpecification200());
        List<OrderListResponse> orders = given()
                .when()
                .get(ORDER_CREATION + "?courierId=")
                .then().log().all()
                .extract().body().jsonPath().getList("orders", OrderListResponse.class);
        orders.stream().forEach(x -> Assert.assertNotNull(x.getId()));
        Assert.assertEquals(30, orders.size());
    }
}