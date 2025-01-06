package com.leveltrack.view;

import com.leveltrack.controller.LoginController;
import com.leveltrack.controller.RegisterController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

class RegisterPanel extends JPanel {
    private final RegisterController registerController = new RegisterController();

    public RegisterPanel(JFrame parentFrame) throws Exception {
        setLayout(new GridLayout(5, 1));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JButton createButton = new JButton("Create Account");
        JButton backButton = new JButton("Back");

        createButton.addActionListener((ActionEvent e) -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            if (registerController.register(name, email, password)) {
                JOptionPane.showMessageDialog(this, "Account created successfully. Please login.");
                parentFrame.getContentPane().removeAll();
                try {
                    parentFrame.add(new LoginPanel(parentFrame, new LoginController(), null));
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                parentFrame.revalidate();
            } else {
                JOptionPane.showMessageDialog(this, "Error creating account.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener((ActionEvent e) -> {
            parentFrame.getContentPane().removeAll();
            try {
                parentFrame.add(new LoginPanel(parentFrame, new LoginController(), null));
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            parentFrame.revalidate();
        });

        add(nameLabel);
        add(nameField);
        add(emailLabel);
        add(emailField);
        add(passwordLabel);
        add(passwordField);
        add(createButton);
        add(backButton);
    }


}