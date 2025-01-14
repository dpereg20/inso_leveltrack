package com.leveltrack.view;

import com.leveltrack.controller.LibraryController;
import com.leveltrack.model.Game;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

class FriendLibraryView extends JPanel {
    private final LibraryController libraryController;
    private final int friendId;
    private final JTable gamesTable;
    private final DefaultTableModel tableModel;

    public FriendLibraryView(int userId, String userRole, int friendId, JFrame parentFrame) throws Exception {
        this.libraryController = new LibraryController();
        this.friendId = friendId;

        setLayout(new BorderLayout());

        // Panel de filtrado y búsqueda
        JPanel filterSearchPanel = new JPanel(new BorderLayout());
        JPanel searchPanel = new JPanel(new BorderLayout());
        JPanel filterPanel = new JPanel(new BorderLayout());

        // Búsqueda por nombre
        JLabel searchLabel = new JLabel("Search by Name: ");
        JTextField searchField = new JTextField(10);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener((ActionEvent e) -> {
            String keyword = searchField.getText().trim();
            if (!keyword.isEmpty()) {
                refreshFriendGamesListByName(keyword);
            } else {
                refreshFriendGamesList();
            }
        });
        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        // Filtrado por género
        JLabel filterLabel = new JLabel("Filter by Genre: ");
        JComboBox<String> genreComboBox = new JComboBox<>();
        genreComboBox.addItem("All");
        List<String> genres = libraryController.getAllGenres();
        for (String genre : genres) {
            genreComboBox.addItem(genre);
        }
        JButton filterButton = new JButton("Filter");
        filterButton.addActionListener((ActionEvent e) -> {
            String selectedGenre = (String) genreComboBox.getSelectedItem();
            if ("All".equalsIgnoreCase(selectedGenre)) {
                refreshFriendGamesList();
            } else {
                refreshFriendGamesListByGenre(selectedGenre);
            }
        });
        filterPanel.add(filterLabel, BorderLayout.WEST);
        filterPanel.add(genreComboBox, BorderLayout.CENTER);
        filterPanel.add(filterButton, BorderLayout.EAST);

        filterSearchPanel.add(searchPanel, BorderLayout.WEST);
        filterSearchPanel.add(filterPanel, BorderLayout.EAST);

        add(filterSearchPanel, BorderLayout.NORTH);

        // Configuración de la tabla
        String[] columnNames = {"ID", "Name", "Genre", "Price", "State", "Score"};
        tableModel = new DefaultTableModel(columnNames, 0);
        gamesTable = new JTable(tableModel);
        refreshFriendGamesList();

        JScrollPane scrollPane = new JScrollPane(gamesTable);
        add(scrollPane, BorderLayout.CENTER);

        // Botón para regresar
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            parentFrame.getContentPane().removeAll();
            try {
                parentFrame.add(new FriendshipView(userId, userRole, parentFrame)); // Cambiar a ManageFriendsView
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            parentFrame.revalidate();
            parentFrame.repaint();
        });

        add(backButton, BorderLayout.SOUTH);
    }

    private void refreshFriendGamesList() {
        tableModel.setRowCount(0);
        List<Game> games = libraryController.getGamesByUserId(friendId);
        for (Game game : games) {
            tableModel.addRow(new Object[]{
                    game.getId(),
                    game.getName(),
                    game.getGenre(),
                    game.getPrice(),
                    game.getState(),
                    game.getScore()
            });
        }
    }

    private void refreshFriendGamesListByName(String name) {
        tableModel.setRowCount(0);
        List<Game> games = libraryController.searchGamesByName(name);
        for (Game game : games) {
            tableModel.addRow(new Object[]{
                    game.getId(),
                    game.getName(),
                    game.getGenre(),
                    game.getPrice(),
                    game.getState(),
                    game.getScore()
            });
        }
    }

    private void refreshFriendGamesListByGenre(String genre) {
        tableModel.setRowCount(0);
        List<Game> games = libraryController.getGamesByGenreUser(friendId, genre);
        for (Game game : games) {
            tableModel.addRow(new Object[]{
                    game.getId(),
                    game.getName(),
                    game.getGenre(),
                    game.getPrice(),
                    game.getState(),
                    game.getScore()
            });
        }
    }
}



