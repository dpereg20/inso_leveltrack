package com.leveltrack.controller;


import com.leveltrack.model.UserBase;
import com.leveltrack.service.UserService;


public class UserManagementController {
    private final UserService userService;


    public UserManagementController() throws Exception {
        this.userService = new UserService();
    }


    public String updateProfile(String identifier, String password, String newName, String newEmail, String newPassword) {
        try {
            UserBase authenticated = userService.authenticate(identifier, password);
            if (authenticated.equals(false)) {
                return "Error: Contraseña incorrecta o usuario no encontrado.";
            }


            boolean success = userService.updateUserProfile(Integer.parseInt(identifier), newName, newEmail, newPassword);
            if (success) {
                return "Perfil actualizado con éxito.";
            } else {
                return "Error al actualizar el perfil.";
            }
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "Ocurrió un error inesperado.";
        }
    }
}





