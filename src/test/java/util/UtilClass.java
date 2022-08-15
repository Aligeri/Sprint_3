package util;

import api.Login;
import api.Order;
import responses.LoginResponseId;
import responses.OrderResponseId;

import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static util.Link.*;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class UtilClass {
    public String getCourierId(String log, String pass) {
        Specifications.installSpecification(Specifications.requestSpecification(BASE_URI),
                Specifications.responseSpecification200());

        Login login = new Login(log, pass);
        LoginResponseId response = given()
                .body(login)
                .when()
                .post(COURIER_LOGIN)
                .then().log().all()
                .extract().as(LoginResponseId.class);
        System.out.println("Getting id.....");
        System.out.println("ID id===>" + response.getId());
        return response.getId();
    }

    public void deleteCourierById(String id) {
        Specifications.installSpecification(Specifications.requestSpecification(BASE_URI),
                Specifications.responseSpecification200());
        when()
                .delete(ID + id)
                .then()
                .log().all();
        System.out.println("Deleted");
    }

    public int getRandomInt() {
        Random random = new Random();
        return random.nextInt(9 + 1);
    }

    public String getRandomDate() {
        int year = ThreadLocalRandom.current().nextInt(2022, 2025 + 1);
        int month = ThreadLocalRandom.current().nextInt(1, 12 + 1);
        int day = ThreadLocalRandom.current().nextInt(1, 28 + 1);
        return year + "-" + month + "-" + day;
    }

    public String randomString(int len){
        final String AB = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(len);
        for(int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    public String randomPhone(int len){
        final String AB = "0123456789";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(len);
        for(int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    public String getOrderId(String firstName, String lastName, String address, int metroStation, String phone,
                             Integer rentTime, String deliveryDate, String comment, String[] color) {
        Specifications.installSpecification(Specifications.requestSpecification(BASE_URI),
                Specifications.responseSpecification201());

        Order order = new Order(firstName,
                lastName,
                address,
                metroStation,
                phone,
                rentTime,
                deliveryDate,
                comment,
                color);
        OrderResponseId response = given()
                .body(order).log().all()
                .when()
                .post(ORDER_CREATION)
                .then()
                .extract().as(OrderResponseId.class);
        System.out.println("Getting id.....");
        System.out.println("ID id===>" + response.getTrack());
        return response.getTrack();
    }

    public void deleteOrderById(String track) {
        Specifications.installSpecification(Specifications.requestSpecification(BASE_URI),
                Specifications.responseSpecification200());
        when()
                .put(ORDER_CANCEL + track)
                .then()
                .log().all();
        System.out.println("Deleted");
    }
}