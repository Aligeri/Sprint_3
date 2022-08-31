package util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Link {
    public static final String BASE_URI = "http://qa-scooter.praktikum-services.ru";
    public static final String COURIER_CREATION = "/api/v1/courier";
    public static final String COURIER_LOGIN = "/api/v1/courier/login";
    public static final String ORDER_CREATION = "/api/v1/orders";
    public static final String ORDER_CANCEL = "/api/v1/orders/cancel?track=";
    public static final String ID = "/api/v1/courier/";
}