package com.leveltrack.view;

import com.leveltrack.controller.AdminController;
import com.leveltrack.controller.LoginController;
import com.leveltrack.model.Game;
import com.leveltrack.model.UserBase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class AdminDashboard extends JPanel {
    private final AdminController adminController;
    private final JFrame parentFrame;

    public AdminDashboard(JFrame parentFrame, int adminId) throws Exception {
        this.parentFrame = parentFrame;
        this.adminController = new AdminController();

        setLayout(new BorderLayout());

        // Main Buttons Panel
        JPanel mainButtonsPanel = new JPanel(new GridLayout(1, 2));
        JButton manageUsersButton = new JButton("Manage Users");
        JButton manageGamesButton = new JButton("Manage Games");
        mainButtonsPanel.add(manageUsersButton);
        mainButtonsPanel.add(manageGamesButton);
        add(mainButtonsPanel, BorderLayout.NORTH);

        // User Management
        manageUsersButton.addActionListener((ActionEvent e) -> openUserManagement(adminId));

        // Game Management
        manageGamesButton.addActionListener((ActionEvent e) -> openGameManagement(adminId));

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener((ActionEvent e) -> logout());

        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(logoutButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void openUserManagement(int adminId) {
        JDialog userDialog = new JDialog(parentFrame, "User Management", true);
        userDialog.setSize(600, 400);
        userDialog.setLayout(new BorderLayout());

        DefaultTableModel userTableModel = new DefaultTableModel(new String[]{"Name", "Email", "Role"}, 0);
        JTable userTable = new JTable(userTableModel);
        refreshUserTable(userTableModel);
        JScrollPane userScrollPane = new JScrollPane(userTable);

        JPanel userButtonsPanel = new JPanel(new GridLayout(1, 2));
        JButton updateRoleButton = new JButton("Update Role");
        JButton deleteUserButton = new JButton("Delete User");

        updateRoleButton.addActionListener((ActionEvent e) -> {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow != -1) {
                String selectedName = userTableModel.getValueAt(selectedRow, 0).toString();
                UserBase selectedUser = adminController.findUserByName(selectedName); // Implement this method
                if (selectedUser != null) {
                    String newRole = (String) JOptionPane.showInputDialog(
                            userDialog,
                            "Select new role:",
                            "Update Role",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            new String[]{"ADMINISTRATOR", "MODERATOR", "REGULAR_USER"},
                            selectedUser.getRole()
                    );
                    if (newRole != null) {
                        adminController.assignUserRole(adminId, selectedUser.getId(), newRole);
                        JOptionPane.showMessageDialog(userDialog, "Role updated successfully!");
                        refreshUserTable(userTableModel);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(userDialog, "Please select a user to update the role.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteUserButton.addActionListener((ActionEvent e) -> {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow != -1) {
                int confirm = JOptionPane.showConfirmDialog(
                        userDialog,
                        "Are you sure you want to delete this user?",
                        "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    String selectedUserName = userTableModel.getValueAt(selectedRow, 0).toString();
                    UserBase selectedUser = adminController.findUserByName(selectedUserName);
                    if (adminController.deleteUser(selectedUser.getId())) {
                        JOptionPane.showMessageDialog(userDialog, "User deleted successfully!");
                        refreshUserTable(userTableModel);
                    } else {
                        JOptionPane.showMessageDialog(userDialog, "Failed to delete user.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        userButtonsPanel.add(updateRoleButton);
        userButtonsPanel.add(deleteUserButton);

        userDialog.add(userScrollPane, BorderLayout.CENTER);
        userDialog.add(userButtonsPanel, BorderLayout.SOUTH);
        userDialog.setVisible(true);
    }

    private void openGameManagement(int adminId) {
        JDialog gameDialog = new JDialog(parentFrame, "Game Management", true);
        gameDialog.setSize(800, 500);
        gameDialog.setLayout(new BorderLayout());

        DefaultTableModel gameTableModel = new DefaultTableModel(new String[]{"Name", "Genre", "Price"}, 0);
        JTable gameTable = new JTable(gameTableModel);
        refreshGameTable(gameTableModel);
        JScrollPane gameScrollPane = new JScrollPane(gameTable);

        JPanel gameButtonsPanel = new JPanel(new GridLayout(1, 3));
        JButton addGameButton = new JButton("Add Game");
        JButton updateGameButton = new JButton("Update Game");
        JButton deleteGameButton = new JButton("Delete Game");

        addGameButton.addActionListener((ActionEvent e) -> openGameForm("Add Game", null, gameTableModel));
        updateGameButton.addActionListener((ActionEvent e) -> {
            int selectedRow = gameTable.getSelectedRow();
            if (selectedRow != -1) {
                String selectedName = gameTableModel.getValueAt(selectedRow, 0).toString();
                Game selectedGame = adminController.findGameByName(selectedName); // Implement this method
                if (selectedGame != null) {
                    openGameForm("Update Game", selectedGame, gameTableModel);
                }
            } else {
                JOptionPane.showMessageDialog(gameDialog, "Please select a game to update.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteGameButton.addActionListener((ActionEvent e) -> {
            int selectedRow = gameTable.getSelectedRow();
            if (selectedRow != -1) {
                int confirm = JOptionPane.showConfirmDialog(
                        gameDialog,
                        "Are you sure you want to delete this game?",
                        "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    String selectedName = gameTableModel.getValueAt(selectedRow, 0).toString();
                    Game selectedGame = adminController.findGameByName(selectedName); // Implement this method
                    if (adminController.deleteGame(selectedGame.getId())) {
                        JOptionPane.showMessageDialog(gameDialog, "Game deleted successfully!");
                        refreshGameTable(gameTableModel);
                    } else {
                        JOptionPane.showMessageDialog(gameDialog, "Failed to delete game.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        gameButtonsPanel.add(addGameButton);
        gameButtonsPanel.add(updateGameButton);
        gameButtonsPanel.add(deleteGameButton);

        gameDialog.add(gameScrollPane, BorderLayout.CENTER);
        gameDialog.add(gameButtonsPanel, BorderLayout.SOUTH);
        gameDialog.setVisible(true);
    }

    private void logout(){
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
        }

    private void refreshUserTable(DefaultTableModel userTableModel) {
        userTableModel.setRowCount(0);
        List<UserBase> users = adminController.getAllUsers(); // Fetch users
        for (UserBase user : users) {
            userTableModel.addRow(new Object[]{user.getName(), user.getEmail(), user.getRole()});
        }
    }

    private void refreshGameTable(DefaultTableModel gameTableModel) {
        gameTableModel.setRowCount(0);
        List<Game> games = adminController.getAllGames(); // Fetch games
        for (Game game : games) {
            gameTableModel.addRow(new Object[]{game.getName(), game.getGenre(), game.getPrice()});
        }
    }

    private void openGameForm(String title, Game game, DefaultTableModel gameTableModel) {
        JDialog dialog = new JDialog(parentFrame, title, true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(4, 2));

        JTextField nameField = new JTextField(game != null ? game.getName() : "");
        JTextField genreField = new JTextField(game != null ? game.getGenre() : "");
        JTextField priceField = new JTextField(game != null ? String.valueOf(game.getPrice()) : "");

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener((ActionEvent e) -> {
            String name = nameField.getText().trim();
            String genre = genreField.getText().trim();
            double price;

            try {
                price = Double.parseDouble(priceField.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid price format.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (game == null) { // Add new game
                Game newGame = new Game(0, name, genre, price, "Available", 0);
                if (adminController.addGame(newGame)) {
                    JOptionPane.showMessageDialog(dialog, "Game added successfully!");
                    refreshGameTable(gameTableModel);
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Failed to add game.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else { // Update existing game
                game.setName(name);
                game.setGenre(genre);
                game.setPrice(price);
                if (adminController.updateGame(game)) {
                    JOptionPane.showMessageDialog(dialog, "Game updated successfully!");
                    refreshGameTable(gameTableModel);
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Failed to update game.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener((ActionEvent e) -> dialog.dispose());

        dialog.add(new JLabel("Name:"));
        dialog.add(nameField);
        dialog.add(new JLabel("Genre:"));
        dialog.add(genreField);
        dialog.add(new JLabel("Price:"));
        dialog.add(priceField);
        dialog.add(saveButton);
        dialog.add(cancelButton);

        dialog.setVisible(true);
    }
}


