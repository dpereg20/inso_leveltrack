package com.leveltrack.controller;

import com.leveltrack.service.UserService;

import javax.swing.*;

/**
 * The RegisterController class handles user registration in the application.
 * It ensures users can create an account while checking for duplicate emails.
 *
 * @author Level Track
 * @since 1.0
 */
public class RegisterController {

    private final UserService userService;

    /**
     * Constructs a new RegisterController and initializes the UserService.
     *
     * @throws Exception if there is an issue initializing the UserService.
     */
    public RegisterController() throws Exception {
        this.userService = new UserService();
    }

    /**
     * Registers a new user in the "Level Track" application.
     *
     * @param name     The name of the user.
     * @param email    The email address of the user.
     * @param password The password for the new account.
     * @return {@code true} if the registration is successful, {@code false} otherwise.
     */
    public boolean register(String name, String email, String password) {
        JOptionPane JOptionPane = new JOptionPane();
        try {
            if (userService.emailExists(email)) {
                JOptionPane.showMessageDialog(null,
                        "Email already in use. Please use a different email.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
            return userService.registerUser(name, email, password, "REGULAR_USER");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null,
                    "Error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
