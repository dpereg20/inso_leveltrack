package com.leveltrack.controller;

import com.leveltrack.service.UserService;

import javax.swing.*;

public class RegisterController {
    private final UserService userService;

    public RegisterController() throws Exception {
        this.userService = new UserService();
    }

    public boolean register(String name, String email, String password) {
        JOptionPane JOptionPane = new JOptionPane();
        try {
            if (userService.emailExists(email)) {
                JOptionPane.showMessageDialog(null, "Email already in use. Please use a different email.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            return userService.registerUser(name, email, password, "REGULAR_USER");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
}