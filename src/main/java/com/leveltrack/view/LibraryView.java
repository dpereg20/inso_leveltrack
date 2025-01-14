package com.leveltrack.view;

import com.leveltrack.controller.LibraryController;
import com.leveltrack.model.Game;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

class LibraryView extends JPanel {
    private final LibraryController libraryController;
    private final int userId;
    private final String userRole;
    private final JTable gamesTable;
    private final DefaultTableModel tableModel;

    public LibraryView(int userId, String userRole, JFrame parentFrame) throws Exception {
        this.userId = userId;
        this.userRole = userRole;
        this.libraryController = new LibraryController();

        setLayout(new BorderLayout());

        // Panel superior para búsqueda y filtrado
        JPanel topPanel = new JPanel(new BorderLayout());

        // Búsqueda por nombre (arriba a la izquierda)
        JPanel searchPanel = new JPanel(new BorderLayout());
        JLabel searchLabel = new JLabel("Search by Name: ");
        JTextField searchField = new JTextField(12);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener((ActionEvent e) -> {
            String keyword = searchField.getText().trim();
            if (!keyword.isEmpty()) {
                refreshGamesListByName(keyword);
            } else {
                refreshGamesList();
            }
        });
        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        // Filtrado por género (arriba a la derecha)
        JPanel filterPanel = new JPanel(new BorderLayout());
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
                refreshGamesList();
            } else {
                refreshGamesListByGenre(selectedGenre);
            }
        });
        filterPanel.add(filterLabel, BorderLayout.WEST);
        filterPanel.add(genreComboBox, BorderLayout.CENTER);
        filterPanel.add(filterButton, BorderLayout.EAST);

        topPanel.add(searchPanel, BorderLayout.WEST);
        topPanel.add(filterPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // Configuración de la tabla de juegos
        String[] columnNames = {"ID", "Name", "Genre", "Price", "State", "Score"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4 || column == 5;
            }
        };
        gamesTable = new JTable(tableModel);
        refreshGamesList();

        JScrollPane scrollPane = new JScrollPane(gamesTable);
        add(scrollPane, BorderLayout.CENTER);

        // Configuración del desplegable para los estados
        String[] states = {"Available", "Playing", "Paused", "Completed", "Dropped", "Wishlist", "Replaying"};
        JComboBox<String> stateComboBox = new JComboBox<>(states);

        TableColumn stateColumn = gamesTable.getColumnModel().getColumn(4); // Columna de estado
        stateColumn.setCellEditor(new DefaultCellEditor(stateComboBox));

        // Detectar cambios en el estado y la puntuación
        gamesTable.getModel().addTableModelListener(e -> {
            if (tableModel.getRowCount() == 0) {
                return;
            }
            int row = e.getFirstRow();
            int column = e.getColumn();
            int gameId = (int) tableModel.getValueAt(row, 0);

            if (column == 4) { // Columna de estado
                String newState = (String) tableModel.getValueAt(row, column);
                try {
                    boolean success = libraryController.updateGameState(gameId, userId, newState);
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Game state updated successfully!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to update game state.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error updating game state: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (column == 5) { // Columna de puntuación
                try {
                    String scoreInput = (String) tableModel.getValueAt(row, column);
                    int score = Integer.parseInt(scoreInput);

                    if (score >= 0 && score <= 10) {
                        boolean success = libraryController.updateGameScore(gameId, userId, score);
                        if (success) {
                            JOptionPane.showMessageDialog(this, "Game score updated successfully!");
                        } else {
                            JOptionPane.showMessageDialog(this, "Failed to update game score.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Score must be between 0 and 10.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                        tableModel.setValueAt("", row, column); // Limpiar el campo inválido
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Score must be a valid integer.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    tableModel.setValueAt(0, row, column); // Limpiar el campo inválido
                }
            }
        });

        // Panel de controles
        JPanel controlsPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        JButton addGameButton = new JButton("Add Game");
        JButton removeGameButton = new JButton("Remove Game");
        JButton backButton = new JButton("Back to Main Menu");

        controlsPanel.add(addGameButton);
        controlsPanel.add(removeGameButton);
        controlsPanel.add(backButton);

        add(controlsPanel, BorderLayout.SOUTH);

        // Acción del botón para añadir juegos
        addGameButton.addActionListener((ActionEvent e) -> {
            String gameNameInput = JOptionPane.showInputDialog("Enter Game Name to Add:");
            try {
                if (libraryController.addGameToLibrary(userId, gameNameInput)) {
                    JOptionPane.showMessageDialog(this, "Game added to library!");
                    refreshGamesList();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add game. Game might not exist or is already in your library.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error adding game: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        removeGameButton.addActionListener((ActionEvent e) ->{
            int selectedRow = gamesTable.getSelectedRow();
            if (selectedRow != -1) {
                String selectedName = gamesTable.getValueAt(selectedRow, 0).toString();
                Game selectedGame = libraryController.findGameByName(selectedName);
                if (libraryController.removeGame(userId, selectedGame.getId())) {
                    JOptionPane.showMessageDialog(this, "Game deleted successfully!");
                    refreshGamesList();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete game.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(this, "Game not found.");
            }
        });

        backButton.addActionListener((ActionEvent e) -> {
            parentFrame.getContentPane().removeAll();
            parentFrame.add(new UserDashboard(parentFrame, userId, userRole));
            parentFrame.revalidate();
            parentFrame.repaint();
        });
    }

    private void refreshGamesList() {
        tableModel.setRowCount(0);
        List<Game> games = libraryController.getGamesByUserId(userId);
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

    private void refreshGamesListByName(String name) {
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

    private void refreshGamesListByGenre(String genre) {
        tableModel.setRowCount(0);
        List<Game> games = libraryController.getGamesByGenreUser(userId, genre);
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


