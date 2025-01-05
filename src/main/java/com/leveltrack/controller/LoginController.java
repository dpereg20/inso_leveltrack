package com.leveltrack.controller;

import com.leveltrack.model.UserBase;
import com.leveltrack.service.UserService;

import javax.swing.*;

public class LoginController {
    private final UserService userService;

    public LoginController() throws Exception {
        this.userService = new UserService();
    }

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
