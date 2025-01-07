package com.leveltrack.view;

import com.leveltrack.controller.LoginController;
import com.leveltrack.controller.UserController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class UserDashboard extends JPanel {
    private final int userId;

    public UserDashboard(JFrame parentFrame, int userId) {
        this.userId = userId;
        setLayout(new GridLayout(4, 1));

        JButton viewLibraryButton = new JButton("View Library");
        JButton manageFriendsButton = new JButton("Manage Friends");
        JButton modifyProfileButton = new JButton("Modify Profile");
        JButton logoutButton = new JButton("Logout");

        viewLibraryButton.addActionListener((ActionEvent e) -> openLibraryView(parentFrame));
        manageFriendsButton.addActionListener((ActionEvent e) -> openFriendshipView(parentFrame));
        modifyProfileButton.addActionListener((ActionEvent e) -> openModifyProfileDialog(parentFrame));
        logoutButton.addActionListener((ActionEvent e) -> {
            parentFrame.getContentPane().removeAll();
            try {
                parentFrame.add(new LoginPanel(parentFrame, new LoginController(), null));
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            parentFrame.revalidate();
        });

        add(viewLibraryButton);
        add(manageFriendsButton);
        add(modifyProfileButton);
        add(logoutButton);
    }

    private void openLibraryView(JFrame parentFrame) {
        parentFrame.getContentPane().removeAll();
        parentFrame.add(new LibraryView(userId, parentFrame));
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    private void openFriendshipView(JFrame parentFrame) {
        parentFrame.getContentPane().removeAll();
        parentFrame.add(new FriendshipView(userId));
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    private void openModifyProfileDialog(JFrame parentFrame) {
        JDialog dialog = new JDialog(parentFrame, "Modify Profile", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setLayout(new GridLayout(5, 1));

        JLabel nameLabel = new JLabel("Name (leave blank to keep current):");
        JTextField nameField = new JTextField();

        JLabel emailLabel = new JLabel("Email (leave blank to keep current):");
        JTextField emailField = new JTextField();

        JLabel passwordLabel = new JLabel("Password (leave blank to keep current):");
        JPasswordField passwordField = new JPasswordField();

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener((ActionEvent e) -> {
            String newName = nameField.getText().trim();
            String newEmail = emailField.getText().trim();
            String newPassword = new String(passwordField.getPassword()).trim();

            try {
                UserController userController = new UserController();
                boolean success = userController.partialUpdateProfile(userId, newName, newEmail, newPassword);
                if (success) {
                    JOptionPane.showMessageDialog(dialog, "Profile updated successfully.");
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Failed to update profile.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(dialog, "An error occurred.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener((ActionEvent e) -> dialog.dispose());

        dialog.add(nameLabel);
        dialog.add(nameField);
        dialog.add(emailLabel);
        dialog.add(emailField);
        dialog.add(passwordLabel);
        dialog.add(passwordField);
        dialog.add(saveButton);
        dialog.add(cancelButton);

        dialog.setVisible(true);
    }
}


