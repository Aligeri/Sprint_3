package responses;

public class BadRequest {
    public String message;

    public BadRequest(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}