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

    public LibraryView(int userId, JFrame parentFrame) throws Exception {
        this.userId = userId;
        this.libraryController = new LibraryController();

        setLayout(new BorderLayout());

        JTextArea gamesList = new JTextArea();
        gamesList.setEditable(false);
        //refreshGamesList(gamesList);

        JScrollPane scrollPane = new JScrollPane(gamesList);
        add(scrollPane, BorderLayout.CENTER);

        JPanel controls = new JPanel(new GridLayout(1, 3));
        JButton addGameButton = new JButton("Add Game");
        JButton filterButton = new JButton("Filter by Genre");
        JButton backButton = new JButton("Back to main menu");

        addGameButton.addActionListener((ActionEvent e) -> {
            String gameIdInput = JOptionPane.showInputDialog("Enter Game ID to Add:");
            try {
                int gameId = Integer.parseInt(gameIdInput);
                if (libraryController.addGameToLibrary(userId, String.valueOf(gameId))) {
                    JOptionPane.showMessageDialog(this, "Game added to library!");
                   // refreshGamesList(gamesList);
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
           // refreshGamesList(gamesList, genre);
        });

       /* backButton.addActionListener((ActionEvent e) -> {
            if(user igual a admin){
                // Crear una nueva instancia de la vista AdminDashboard
                AdminDashboard adminDashboard = new AdminDashboard(parentFrame, userId);

                // Cambiar el contenido del JFrame actual a la vista de AdminDashboard
                parentFrame.setContentPane(adminDashboard);  // Establece el contenedor del JFrame
                parentFrame.revalidate();  // Actualiza el contenedor
                parentFrame.repaint();     // Redibuja la ventana
                }else if (user igual a moderador){
                    // Crear una nueva instancia de la vista ModeratorDashboard
                    ModeratorDashboard moderatorDashboard = new ModeratorDashboard(parentFrame, userId);

                    // Cambiar el contenido del JFrame actual a la vista de UserDashboard
                    parentFrame.setContentPane(moderatorDashboard);  // Establece el contenedor del JFrame
                    parentFrame.revalidate();  // Actualiza el contenedor
                    parentFrame.repaint();     // Redibuja la ventana
                }else{
                    // Crear una nueva instancia de la vista UserDashboard
                    UserDashboard userDashboard = new UserDashboard(parentFrame, userId);

                     // Cambiar el contenido del JFrame actual a la vista de UserDashboard
                    parentFrame.setContentPane(userDashboard);  // Establece el contenedor del JFrame
                    parentFrame.revalidate();  // Actualiza el contenedor
                    parentFrame.repaint();     // Redibuja la ventana
                }
            });*/

        controls.add(addGameButton);
        controls.add(filterButton);
        controls.add(backButton);
        add(controls, BorderLayout.SOUTH);
    }
/*
    private void refreshGamesList(JTextArea gamesList) {
        List<Game> games = libraryController.getAllGames();
        displayGames(games, gamesList);
    }

    private void refreshGamesList(JTextArea gamesList, String genre) {
        List<Game> games = libraryController.getGamesByGenre(genre);
        displayGames(games, gamesList);
    }*/

    private void displayGames(List<Game> games, JTextArea gamesList) {
        StringBuilder sb = new StringBuilder();
        for (Game game : games) {
            sb.append(game).append("\n");
        }
        gamesList.setText(sb.toString());
    }
}