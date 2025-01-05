package com.leveltrack.view;

import com.leveltrack.controller.LoginController;
import com.leveltrack.model.UserBase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

class LoginPanel extends JPanel {
    public interface LoginCallback {
        void onLoginSuccess(UserBase user) throws Exception;
    }

    private final LoginController loginController;

    public LoginPanel(JFrame parentFrame, LoginController loginController, LoginCallback callback) {
        this.loginController = loginController;
        setLayout(new GridLayout(4, 1));

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Create Account");

        loginButton.addActionListener((ActionEvent e) -> {
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Email and password are required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            UserBase user = loginController.login(email, password);
            if (user != null) {
                try {
                    callback.onLoginSuccess(user);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        registerButton.addActionListener((ActionEvent e) -> {
            parentFrame.getContentPane().removeAll();
            try {
                parentFrame.add(new RegisterPanel(parentFrame));
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            parentFrame.revalidate();
        });

        add(emailLabel);
        add(emailField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);
        add(registerButton);
    }
}
