package com.leveltrack.view;

import com.leveltrack.controller.LibraryController;
import com.leveltrack.model.Game;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

class GameDatabaseView extends JPanel {
    private final LibraryController libraryController;

    public GameDatabaseView(int userId, String userRole, JFrame parentFrame) throws Exception {
        setLayout(new BorderLayout());

        libraryController = new LibraryController();

        // Panel principal de la tabla de juegos
        JPanel databasePanel = new JPanel(new BorderLayout());
        JLabel databaseLabel = new JLabel("Available Games in Database:");
        databaseLabel.setHorizontalAlignment(SwingConstants.CENTER);
        databaseLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JTable databaseTable = new JTable(new DefaultTableModel(new String[]{"ID", "Name", "Genre", "Price"}, 0));
        refreshGameDatabase((DefaultTableModel) databaseTable.getModel());

        databasePanel.add(databaseLabel, BorderLayout.NORTH);
        databasePanel.add(new JScrollPane(databaseTable), BorderLayout.CENTER);

        // Panel de búsqueda
        JPanel searchPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Búsqueda por nombre
        gbc.gridx = 0;
        gbc.gridy = 0;
        searchPanel.add(new JLabel("Search by Name:"), gbc);

        JTextField searchField = new JTextField(15);
        gbc.gridx = 1;
        searchPanel.add(searchField, gbc);

        JButton searchButton = new JButton("Search");
        gbc.gridx = 2;
        searchPanel.add(searchButton, gbc);

        searchButton.addActionListener((ActionEvent e) -> {
            String keyword = searchField.getText().trim();
            if (!keyword.isEmpty()) {
                refreshGameDatabase((DefaultTableModel) databaseTable.getModel(), keyword);
            } else {
                refreshGameDatabase((DefaultTableModel) databaseTable.getModel());
            }
        });

        // Búsqueda por género
        gbc.gridx = 0;
        gbc.gridy = 1;
        searchPanel.add(new JLabel("Search by Genre:"), gbc);

        List<String> genres = libraryController.getAllGenres();
        genres.add(0, "All"); // Opción para mostrar todos
        JComboBox<String> genreComboBox = new JComboBox<>(genres.toArray(new String[0]));
        gbc.gridx = 1;
        searchPanel.add(genreComboBox, gbc);

        JButton searchByGenreButton = new JButton("Filter");
        gbc.gridx = 2;
        searchPanel.add(searchByGenreButton, gbc);

        searchByGenreButton.addActionListener((ActionEvent e) -> {
            String selectedGenre = (String) genreComboBox.getSelectedItem();
            if (!"All".equalsIgnoreCase(selectedGenre)) {
                refreshGameDatabaseByGenre((DefaultTableModel) databaseTable.getModel(), selectedGenre);
            } else {
                refreshGameDatabase((DefaultTableModel) databaseTable.getModel());
            }
        });

        // Botón para añadir juego
        JButton addGameButton = new JButton("Add to Library");
        addGameButton.addActionListener((ActionEvent e) -> {
            int selectedRow = databaseTable.getSelectedRow();
            if (selectedRow != -1) {
                int gameId = (int) databaseTable.getValueAt(selectedRow, 0);
                String gameName = (String) databaseTable.getValueAt(selectedRow, 1);
                try {
                    boolean success = libraryController.addGameToLibrary(userId, gameName);
                    JOptionPane.showMessageDialog(this, success ? "Game added to library!" : "Game already in library or not found.", "Info", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error adding game: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a game to add.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        searchPanel.add(addGameButton, gbc);

        // Botón para regresar
        JButton backButton = new JButton("Back");
        backButton.addActionListener((ActionEvent e) -> {
            parentFrame.getContentPane().removeAll();
            parentFrame.add(new UserDashboard(parentFrame, userId, userRole));
            parentFrame.revalidate();
            parentFrame.repaint();
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(backButton);

        add(databasePanel, BorderLayout.CENTER);
        add(searchPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void refreshGameDatabase(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        List<Game> games = libraryController.getAllGames();
        for (Game game : games) {
            tableModel.addRow(new Object[]{game.getId(), game.getName(), game.getGenre(), game.getPrice()});
        }
    }

    private void refreshGameDatabase(DefaultTableModel tableModel, String keyword) {
        tableModel.setRowCount(0);
        List<Game> games = libraryController.searchGamesByName(keyword);
        for (Game game : games) {
            tableModel.addRow(new Object[]{game.getId(), game.getName(), game.getGenre(), game.getPrice()});
        }
    }

    private void refreshGameDatabaseByGenre(DefaultTableModel tableModel, String genre) {
        tableModel.setRowCount(0);
        List<Game> games = libraryController.searchGamesByGenre(genre);
        for (Game game : games) {
            tableModel.addRow(new Object[]{game.getId(), game.getName(), game.getGenre(), game.getPrice()});
        }
    }
}


