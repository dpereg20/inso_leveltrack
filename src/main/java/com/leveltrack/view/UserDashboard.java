package com.leveltrack.view;

import com.leveltrack.controller.LoginController;
import com.leveltrack.controller.UserController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class UserDashboard extends JPanel {
    private final int userId;
    private String userRole;


    public UserDashboard(JFrame parentFrame, int userId, String userRole) {
        this.userId = userId;
        this.userRole = userRole;
        setLayout(new GridLayout(4, 1));

        JButton viewLibraryButton = new JButton("View Library");
        JButton manageFriendsButton = new JButton("Manage Friends");
        JButton modifyProfileButton = new JButton("Modify Profile");
        JButton logoutButton = new JButton("Logout");

        viewLibraryButton.addActionListener((ActionEvent e) -> {
            try {
                openLibraryView(parentFrame);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        manageFriendsButton.addActionListener((ActionEvent e) -> {
            try {
                openFriendshipView(parentFrame);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        modifyProfileButton.addActionListener((ActionEvent e) -> openModifyProfileDialog(parentFrame));
        logoutButton.addActionListener((ActionEvent e) -> {
            parentFrame.getContentPane().removeAll();
            try {
                parentFrame.add(new LoginPanel(parentFrame, new LoginController(), (user) -> {
                    // Callback para cuando el usuario se loguee
                    parentFrame.getContentPane().removeAll();
                    if ("ADMINISTRATOR".equalsIgnoreCase(user.getRole())) {
                        parentFrame.add(new AdminDashboard(parentFrame, user.getId()));
                    } else {
                        parentFrame.add(new UserDashboard(parentFrame, user.getId(), user.getRole()));
                    }
                    parentFrame.revalidate();
                    parentFrame.repaint();
                }));
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

    private void openLibraryView(JFrame parentFrame) throws Exception {
        parentFrame.getContentPane().removeAll();
        parentFrame.add(new LibraryView(this.userId, this.userRole,  parentFrame));
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    private void openFriendshipView(JFrame parentFrame) throws Exception {
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



