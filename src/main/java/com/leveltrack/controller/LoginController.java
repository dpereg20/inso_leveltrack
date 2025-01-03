package com.leveltrack.controller;

import com.leveltrack.model.UserBase;
import com.leveltrack.service.UserService;

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
