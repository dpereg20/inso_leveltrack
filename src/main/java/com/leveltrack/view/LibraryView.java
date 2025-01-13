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


        // Título
        JLabel titleLabel = new JLabel("Your Library", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);


        // Configuración de la tabla
        String[] columnNames = {"ID", "Name", "Genre", "Price", "State", "Score"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Permitir edición solo en la columna de Score y State
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


        // Panel de controles
        JPanel controlsPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        JButton addGameButton = new JButton("Add Game");
        JButton updateScoreButton = new JButton("Update Score");
        JButton backButton = new JButton("Back to Main Menu");


        controlsPanel.add(addGameButton);
        controlsPanel.add(updateScoreButton);
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


        // Acción del botón para actualizar la puntuación
        updateScoreButton.addActionListener((ActionEvent e) -> {
            int selectedRow = gamesTable.getSelectedRow();
            if (selectedRow != -1) {
                int gameId = (int) tableModel.getValueAt(selectedRow, 0);
                String inputScore = JOptionPane.showInputDialog("Enter a new score (0-10):");
                try {
                    int score = Integer.parseInt(inputScore);
                    if (score >= 0 && score <= 10) {
                        boolean success = libraryController.updateGameScore(gameId, userId, score);
                        if (success) {
                            JOptionPane.showMessageDialog(this, "Game score updated successfully!");
                            refreshGamesList();
                        } else {
                            JOptionPane.showMessageDialog(this, "Failed to update the score. Make sure the game is in your library.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Score must be between 0 and 10.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid integer for the score.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a game to update its score.", "No Game Selected", JOptionPane.WARNING_MESSAGE);
            }
        });



        // Acción del botón para regresar
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
                    libraryController.getGameScore(game.getId(), userId) // Obtener la puntuación actual
            });
        }
    }
}





