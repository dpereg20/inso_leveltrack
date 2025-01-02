package com.leveltrack.controller;

import com.leveltrack.service.UserService;

public class RegisterController {
    private final UserService userService;

    public RegisterController() throws Exception {
        this.userService = new UserService();
    }

    public String register(String name, String email, String password, String role) {
        try {
            boolean success = userService.registerUser(name, email, password, role);
            if (success) {
                return "Usuario registrado con éxito.";
            } else {
                return "Error al registrar el usuario.";
            }
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "Ocurrió un error inesperado.";
        }
    }
}