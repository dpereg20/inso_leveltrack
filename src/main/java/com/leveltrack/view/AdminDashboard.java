package com.leveltrack.view;

import com.leveltrack.controller.AdminController;
import com.leveltrack.controller.LoginController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

class AdminDashboard extends JPanel {
    private final AdminController adminController;

    public AdminDashboard(JFrame parentFrame, int adminId) throws Exception {
        this.adminController = new AdminController();

        setLayout(new GridLayout(4, 1));

        // Components for role modification
        JLabel userIdLabel = new JLabel("User ID:");
        JTextField userIdField = new JTextField();
        JLabel roleLabel = new JLabel("New Role:");
        JTextField roleField = new JTextField();

        JButton assignRoleButton = new JButton("Modify Role");
        JButton logoutButton = new JButton("Logout");

        assignRoleButton.addActionListener((ActionEvent e) -> {
            try {
                int userId = Integer.parseInt(userIdField.getText());
                String newRole = roleField.getText().trim();
                if (newRole.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Role cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                adminController.assignUserRole(adminId, userId, newRole);
                JOptionPane.showMessageDialog(this, "Role updated successfully.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid User ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        logoutButton.addActionListener((ActionEvent e) -> {
            parentFrame.getContentPane().removeAll();
            try {
                parentFrame.add(new LoginPanel(parentFrame, new LoginController(), user -> {
                    parentFrame.getContentPane().removeAll();
                    if ("ADMINISTRATOR".equalsIgnoreCase(user.getRole())) {
                        parentFrame.add(new AdminDashboard(parentFrame, user.getId()));
                    } else {
                        parentFrame.add(new UserDashboard(parentFrame, user.getId()));
                    }
                    parentFrame.revalidate();
                    parentFrame.repaint();
                }));
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            parentFrame.revalidate();
        });

        // Add components to panel
        add(userIdLabel);
        add(userIdField);
        add(roleLabel);
        add(roleField);
        add(assignRoleButton);
        add(logoutButton);
    }
}
