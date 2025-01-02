package controller;

import model.UserBase;
import service.UserService;

public class LoginController {
    private final UserService userService;

    public LoginController() throws Exception {
        this.userService = new UserService();
    }

    public String login(String email, String password) {
        UserBase user = userService.authenticate(email, password);
        if (user != null) {
            return "Login exitoso. Bienvenido, " + user.getName() + "!";
        } else {
            return "Credenciales incorrectas. Intenta de nuevo.";
        }
    }
}
