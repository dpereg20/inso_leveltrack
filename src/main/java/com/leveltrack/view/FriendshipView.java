package com.leveltrack.view;

import com.leveltrack.controller.FriendshipController;
import com.leveltrack.model.Friendship;
import com.leveltrack.model.UserBase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class FriendshipView extends JPanel {
    private final FriendshipController friendshipController;

    public FriendshipView(int userId, String userRole, JFrame parentFrame) throws Exception {
        setLayout(new BorderLayout());
        friendshipController = new FriendshipController();

        // Tabla de amigos
        JPanel friendsPanel = new JPanel(new BorderLayout());
        JLabel friendsLabel = new JLabel("Friends:");
        JTable friendsTable = new JTable(new DefaultTableModel(new String[]{"Name", "Email"}, 0));
        refreshFriendsList(userId, (DefaultTableModel) friendsTable.getModel());
        friendsPanel.add(friendsLabel, BorderLayout.NORTH);
        friendsPanel.add(new JScrollPane(friendsTable), BorderLayout.CENTER);

        add(friendsPanel, BorderLayout.WEST);

        // Tabla de solicitudes pendientes
        JPanel requestsPanel = new JPanel(new BorderLayout());
        JLabel requestsLabel = new JLabel("Pending Friend Requests:");
        JTable requestsTable = new JTable(new DefaultTableModel(new String[]{"Request ID", "Requester Name", "Status"}, 0));
        refreshPendingRequests(userId, (DefaultTableModel) requestsTable.getModel());
        requestsPanel.add(requestsLabel, BorderLayout.NORTH);
        requestsPanel.add(new JScrollPane(requestsTable), BorderLayout.CENTER);

        // Botones para aceptar/rechazar solicitudes
        JButton acceptButton = new JButton("Accept");
        JButton rejectButton = new JButton("Reject");

        acceptButton.addActionListener((ActionEvent e) -> {
            int selectedRow = requestsTable.getSelectedRow();
            if (selectedRow != -1) {
                int requestId = (int) requestsTable.getValueAt(selectedRow, 0);
                boolean success = friendshipController.respondToFriendRequest(requestId, "Accepted");
                JOptionPane.showMessageDialog(this, success ? "Request accepted!" : "Failed to accept request.");
                refreshPendingRequests(userId, (DefaultTableModel) requestsTable.getModel());
                refreshFriendsList(userId, (DefaultTableModel) friendsTable.getModel());
            } else {
                JOptionPane.showMessageDialog(this, "Please select a request to accept.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        rejectButton.addActionListener((ActionEvent e) -> {
            int selectedRow = requestsTable.getSelectedRow();
            if (selectedRow != -1) {
                int requestId = (int) requestsTable.getValueAt(selectedRow, 0);
                boolean success = friendshipController.respondToFriendRequest(requestId, "Rejected");
                JOptionPane.showMessageDialog(this, success ? "Request rejected!" : "Failed to reject request.");
                refreshPendingRequests(userId, (DefaultTableModel) requestsTable.getModel());
            } else {
                JOptionPane.showMessageDialog(this, "Please select a request to reject.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2));
        buttonsPanel.add(acceptButton);
        buttonsPanel.add(rejectButton);
        requestsPanel.add(buttonsPanel, BorderLayout.SOUTH);

        add(requestsPanel, BorderLayout.CENTER);

        // Botón para enviar solicitud de amistad
        JButton sendRequestButton = new JButton("Send Friend Request");
        sendRequestButton.addActionListener((ActionEvent e) -> {
            String receiverIdInput = JOptionPane.showInputDialog("Enter the User ID of the person you want to add:");
            try {
                int receiverId = Integer.parseInt(receiverIdInput);
                boolean success = friendshipController.sendFriendRequest(userId, receiverId);
                JOptionPane.showMessageDialog(this, success ? "Friend request sent!" : "Failed to send friend request.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid User ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Botón "Back" para regresar al menú de usuario
        JButton backButton = new JButton("Back");
        backButton.addActionListener((ActionEvent e) -> {
            parentFrame.getContentPane().removeAll();
            try {
                parentFrame.add(new UserDashboard(parentFrame, userId, userRole));  // Regresar al dashboard del usuario
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            parentFrame.revalidate();
            parentFrame.repaint();
        });

     /*   JButton viewFriendLibraryButton = new JButton("View Friend Library");

        viewFriendLibraryButton.addActionListener((ActionEvent e) -> {
            int selectedRow = friendsTable.getSelectedRow();
            if (selectedRow != -1) {
                String email = (String) friendsTable.getValueAt(selectedRow, 1);  // Obtener el email de la fila seleccionada

                // Obtener el userId del email
                int userId = libraryService.getUserIdByEmail(email);

                if (userId != -1) {
                    // Obtener la lista de juegos de la librería del usuario seleccionado
                    List<Game> userLibrary = gameController.getGamesByUserId(userId);

                    // Verificar si la librería tiene juegos y mostrarlos
                    if (!userLibrary.isEmpty()) {
                        JTextArea libraryTextArea = new JTextArea();
                        libraryTextArea.setEditable(false);  // No editable
                        displayGames(userLibrary, libraryTextArea);  // Usamos el método displayGames para mostrar los juegos

                        // Mostrar la librería en un JOptionPane
                        JOptionPane.showMessageDialog(this, new JScrollPane(libraryTextArea), "User Library", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "This user does not have any games in their library.", "Empty Library", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "User not found with email: " + email, "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a user to view their library.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        */


        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(sendRequestButton);
        bottomPanel.add(backButton);
    //    bottomPanel.add(viewFriendLibrary);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void refreshFriendsList(int userId, DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        List<UserBase> friends = friendshipController.getFriends(userId);
        for (UserBase friend : friends) {
            tableModel.addRow(new Object[]{friend.getName(), friend.getEmail()});
        }
    }

    private void refreshPendingRequests(int userId, DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        List<Friendship> requests = friendshipController.getFriendRequests(userId);
        for (Friendship request : requests) {
            List<UserBase> searchResults = friendshipController.searchUsers(String.valueOf(request.getRequesterId()));
            if (!searchResults.isEmpty()) {
                UserBase requester = searchResults.get(0);
                tableModel.addRow(new Object[]{request.getId(), requester.getName(), request.getStatus()});
            } else {
                tableModel.addRow(new Object[]{request.getId(), "Unknown User", request.getStatus()});
            }
        }
    }
}


