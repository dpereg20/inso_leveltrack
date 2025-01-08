package com.leveltrack.view;

import com.leveltrack.controller.LibraryController;
import com.leveltrack.model.Game;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class LibraryView extends JPanel {
    private final LibraryController libraryController;
    private final int userId;
    private final JTable gamesTable;

    public LibraryView(int userId, JFrame parentFrame) {
        this.userId = userId;
        setLayout(new BorderLayout());

        try {
            libraryController = new LibraryController();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize LibraryController", e);
        }

        // Create games table
        String[] columnNames = {"ID", "Name", "Genre", "Price", "State"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        gamesTable = new JTable(tableModel);
        refreshGameList(tableModel);

        JScrollPane scrollPane = new JScrollPane(gamesTable);
        add(scrollPane, BorderLayout.CENTER);

        // Control Panel
        JPanel controlPanel = new JPanel(new GridLayout(3, 1));
        JButton addGameButton = new JButton("Add Selected Game");
        JButton backButton = new JButton("Back");
        JButton refreshButton = new JButton("Refresh Game List");

        addGameButton.addActionListener((ActionEvent e) -> {
            int selectedRow = gamesTable.getSelectedRow();
            if (selectedRow != -1) {
                int gameId = (int) tableModel.getValueAt(selectedRow, 0);
                try {
                    boolean success = libraryController.addGameToLibrary(userId, gameId);
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Game added successfully!");
                        refreshGameList(tableModel);
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to add game. It might already be in the library.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error adding game: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a game to add.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });


        refreshButton.addActionListener((ActionEvent e) -> refreshGameList(tableModel));

        backButton.addActionListener((ActionEvent e) -> {
            parentFrame.getContentPane().removeAll();
            parentFrame.add(new UserDashboard(parentFrame, userId));
            parentFrame.revalidate();
            parentFrame.repaint();
        });

        controlPanel.add(addGameButton);
        controlPanel.add(refreshButton);
        controlPanel.add(backButton);
        add(controlPanel, BorderLayout.EAST);
    }

    private void refreshGameList(DefaultTableModel tableModel) {
        List<Game> games = libraryController.getAvailableGames();
        tableModel.setRowCount(0); // Clear the table
        for (Game game : games) {
            tableModel.addRow(new Object[]{
                    game.getId(),
                    game.getName(),
                    game.getGenre(),
                    game.getPrice(),
                    game.getState()
            });
        }
    }
}


