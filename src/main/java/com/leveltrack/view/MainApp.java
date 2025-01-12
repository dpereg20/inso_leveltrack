package com.leveltrack.view;

import com.leveltrack.controller.LoginController;

import javax.swing.*;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("LevelTrack");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);

            LoginController loginController = null;
            try {
                loginController = new LoginController();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            // Pass a valid callback to the LoginPanel
            frame.add(new LoginPanel(frame, loginController, user -> {
                frame.getContentPane().removeAll();
                System.out.println(user.getRole());
                if ("ADMINISTRATOR".equalsIgnoreCase(user.getRole())) {
                    frame.add(new AdminDashboard(frame, user.getId()));
                } else {
                    frame.add(new UserDashboard(frame, user.getId(), user.getRole()));
                }
                frame.revalidate();
                frame.repaint();
            }));
            frame.setVisible(true);
        });
    }
}
