package util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Error {
    public static final String ERROR_LOGIN_400 = "Недостаточно данных для входа";
    public static final String ERROR_LOGIN_409 = "Учетная запись не найдена";
    public static final String ERROR_COURIER_400 = "Недостаточно данных для создания учетной записи";
    public static final String ERROR_COURIER_409 = "Этот логин уже используется. Попробуйте другой.";
}