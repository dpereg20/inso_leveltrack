package com.leveltrack.view;

import com.leveltrack.controller.LibraryController;
import com.leveltrack.model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

class LibraryView extends JPanel {
    private final LibraryController libraryController;
    private final int userId;

    public LibraryView(int userId, JFrame parentFrame) {
        this.userId = userId;
        setLayout(new BorderLayout());

        try {
            libraryController = new LibraryController();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize LibraryController", e);
        }

        // Game List Panel
        JTextArea gameList = new JTextArea();
        gameList.setEditable(false);
        refreshGameList(gameList);

        JScrollPane scrollPane = new JScrollPane(gameList);
        add(scrollPane, BorderLayout.CENTER);

        // Control Panel
        JPanel controlPanel = new JPanel(new GridLayout(4, 1));
        JButton addGameButton = new JButton("Add Game");
        JButton removeGameButton = new JButton("Remove Game");
        JButton updateGameStateButton = new JButton("Change Game State");
        JButton backButton = new JButton("Back");

        addGameButton.addActionListener((ActionEvent e) -> {
            String name = JOptionPane.showInputDialog("Enter Game Name:");
            String genre = JOptionPane.showInputDialog("Enter Game Genre:");
            String priceInput = JOptionPane.showInputDialog("Enter Game Price:");
            String state = JOptionPane.showInputDialog("Enter Game State (Available, Playing, Completed):");

            try {
                double price = Double.parseDouble(priceInput);
                Game game = new Game(0, name, genre, price, state);
                boolean success = libraryController.addGame(userId, game);
                JOptionPane.showMessageDialog(this, success ? "Game added successfully!" : "Failed to add game.");
                refreshGameList(gameList);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid price format.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        removeGameButton.addActionListener((ActionEvent e) -> {
            String gameIdInput = JOptionPane.showInputDialog("Enter Game ID to Remove:");
            try {
                int gameId = Integer.parseInt(gameIdInput);
                boolean success = libraryController.removeGame(userId, gameId);
                JOptionPane.showMessageDialog(this, success ? "Game removed successfully!" : "Failed to remove game.");
                refreshGameList(gameList);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Game ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        updateGameStateButton.addActionListener((ActionEvent e) -> {
            String gameIdInput = JOptionPane.showInputDialog("Enter Game ID:");
            String newState = JOptionPane.showInputDialog("Enter New State (Available, Playing, Completed):");

            try {
                int gameId = Integer.parseInt(gameIdInput);
                boolean success = libraryController.changeGameState(gameId, newState);
                JOptionPane.showMessageDialog(this, success ? "Game state updated successfully!" : "Failed to update game state.");
                refreshGameList(gameList);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Game ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener((ActionEvent e) -> {
            parentFrame.getContentPane().removeAll();
            parentFrame.add(new UserDashboard(parentFrame, userId));
            parentFrame.revalidate();
            parentFrame.repaint();
        });

        controlPanel.add(addGameButton);
        controlPanel.add(removeGameButton);
        controlPanel.add(updateGameStateButton);
        controlPanel.add(backButton);
        add(controlPanel, BorderLayout.EAST);
    }

    private void refreshGameList(JTextArea gameList) {
        List<Game> games = libraryController.getGames(userId);
        StringBuilder sb = new StringBuilder();
        for (Game game : games) {
            sb.append(game).append("\n");
        }
        gameList.setText(sb.toString());
    }
}

