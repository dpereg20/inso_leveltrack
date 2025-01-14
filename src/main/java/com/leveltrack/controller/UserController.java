package com.leveltrack.controller;

import com.leveltrack.model.UserBase;
import com.leveltrack.service.UserService;

/**
 * The UserController class manages user-related operations such as updating user profiles in the application.
 *
 * @author Level Track
 * @since 1.0
 */
public class UserController {

    private final UserService userService;

    /**
     * Constructs a new UserController and initializes the UserService.
     *
     * @throws Exception if there is an issue initializing the UserService.
     */
    public UserController() throws Exception {
        this.userService = new UserService();
    }

    /**
     * Updates the user's profile with new information.
     *
     * @param identifier The unique identifier (e.g., user ID or email) for the user.
     * @param password   The current password of the user.
     * @param newName    The new name for the user (optional, can be null).
     * @param newEmail   The new email address for the user (optional, can be null).
     * @param newPassword The new password for the user (optional, can be null).
     * @return A message indicating the success or failure of the operation.
     */
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

    /**
     * Partially updates the user's profile with specific fields.
     *
     * @param userId   The unique ID of the user.
     * @param name     The new name for the user (optional, can be null).
     * @param email    The new email address for the user (optional, can be null).
     * @param password The new password for the user (optional, can be null).
     * @return {@code true} if the update is successful, {@code false} otherwise.
     */
    public boolean partialUpdateProfile(int userId, String name, String email, String password) {
        try {
            return userService.partialUpdateUserProfile(userId, name, email, password);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
