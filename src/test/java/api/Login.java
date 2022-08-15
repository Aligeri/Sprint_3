package api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Login {

    private String id;
    private String login;
    private String password;

    public Login(String login, String password) {
        this.login = login;
        this.password = password;
    }
}