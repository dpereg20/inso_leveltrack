package com.leveltrack.controller;

import com.leveltrack.model.UserBase;
import com.leveltrack.service.UserService;

import javax.swing.*;
/**
 * The LoginController class handles the login process for the "Level Track" application.
 * It manages user authentication by interacting with the UserService.
 *
 * @author Level Track
 * @since 1.0
 */
public class LoginController {

    private final UserService userService;

    /**
     * Constructs a new LoginController and initializes the UserService.
     *
     * @throws Exception if there is an issue initializing the UserService.
     */
    public LoginController() throws Exception {
        this.userService = new UserService();
    }

    /**
     * Authenticates a user using their email and password.
     *
     * @param email    The email address of the user.
     * @param password The password of the user.
     * @return A {@code UserBase} object representing the authenticated user,
     *         or {@code null} if authentication fails.
     */
    public UserBase login(String email, String password) {
        try {
            UserBase user = userService.authenticate(email, password);
            if (user != null) {
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Return null if authentication fails
    }
}
