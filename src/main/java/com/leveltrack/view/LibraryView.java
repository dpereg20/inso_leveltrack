package com.leveltrack.view;

import com.leveltrack.controller.LibraryController;
import com.leveltrack.controller.LoginController;
import com.leveltrack.model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

class LibraryView extends JPanel {
    private final LibraryController libraryController;
    private final int userId;
    private String userRole;

    public LibraryView(int userId, String userRole, JFrame parentFrame) throws Exception {
        this.userId = userId;
        this.userRole = userRole;
        this.libraryController = new LibraryController();

        setLayout(new BorderLayout());

        JTextArea gamesList = new JTextArea();
        gamesList.setEditable(false);
        refreshGamesList(gamesList);

        JScrollPane scrollPane = new JScrollPane(gamesList);
        add(scrollPane, BorderLayout.CENTER);

        JPanel controls = new JPanel(new GridLayout(1, 3));
        JButton addGameButton = new JButton("Add Game");
        JButton filterButton = new JButton("Filter by Genre");
        JButton backButton = new JButton("Back to main menu");

        addGameButton.addActionListener((ActionEvent e) -> {
            String gameNameInput = JOptionPane.showInputDialog("Enter Game name to Add:");
            try {
                if (libraryController.addGameToLibrary(userId, String.valueOf(gameNameInput))) {
                    JOptionPane.showMessageDialog(this, "Game added to library!");
                    refreshGamesList(gamesList);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add game.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Game ID.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        filterButton.addActionListener((ActionEvent e) -> {
            String genre = JOptionPane.showInputDialog("Enter Genre to Filter:");
            refreshGamesList(gamesList, genre);
        });

        backButton.addActionListener((ActionEvent e) -> {
            parentFrame.getContentPane().removeAll();
            parentFrame.add(new UserDashboard(parentFrame, this.userId, this.userRole));
            parentFrame.revalidate();
            parentFrame.repaint();
        });

        controls.add(addGameButton);
        controls.add(filterButton);
        controls.add(backButton);
        add(controls, BorderLayout.SOUTH);
    }

    private void refreshGamesList(JTextArea gamesList) {
        List<Game> games = libraryController.getGamesByUserId(this.userId);
        displayGames(games, gamesList);
    }

    private void refreshGamesList(JTextArea gamesList, String genre) {
        List<Game> games;
        if (!genre.isEmpty()) {
            games = libraryController.getGamesByGenre(this.userId, genre);
        }else{
            games = libraryController.getGamesByUserId(userId);
        }
        displayGames(games, gamesList);
    }

    private void displayGames(List<Game> games, JTextArea gamesList) {
        StringBuilder sb = new StringBuilder();
        for (Game game : games) {
            sb.append(game).append("\n");
        }
        gamesList.setText(sb.toString());
    }
}