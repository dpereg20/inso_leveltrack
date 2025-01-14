package com.leveltrack.view;

import com.leveltrack.controller.FriendshipController;
import com.leveltrack.controller.LibraryController;
import com.leveltrack.model.Friendship;
import com.leveltrack.model.Game;
import com.leveltrack.model.UserBase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

class FriendshipView extends JPanel {
    private final FriendshipController friendshipController;

    public FriendshipView(int userId, String userRole, JFrame parentFrame) throws Exception {
        setLayout(new BorderLayout());
        friendshipController = new FriendshipController();

        JPanel friendsPanel = new JPanel(new BorderLayout());
        JLabel friendsLabel = new JLabel("Friends:");
        JTable friendsTable = new JTable(new DefaultTableModel(new String[]{"Name", "Email"}, 0));
        refreshFriendsList(userId, (DefaultTableModel) friendsTable.getModel());
        friendsPanel.add(friendsLabel, BorderLayout.NORTH);
        friendsPanel.add(new JScrollPane(friendsTable), BorderLayout.CENTER);

        add(friendsPanel, BorderLayout.WEST);

        JPanel requestsPanel = new JPanel(new BorderLayout());
        JLabel requestsLabel = new JLabel("Pending Friend Requests:");
        JTable requestsTable = new JTable(new DefaultTableModel(new String[]{"Request ID", "Requester Email", "Status"}, 0));
        refreshPendingRequests(userId, (DefaultTableModel) requestsTable.getModel());
        requestsPanel.add(requestsLabel, BorderLayout.NORTH);
        requestsPanel.add(new JScrollPane(requestsTable), BorderLayout.CENTER);

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

        JButton sendRequestButton = new JButton("Send Friend Request");
        sendRequestButton.addActionListener((ActionEvent e) -> {
            String receiverEmail = JOptionPane.showInputDialog("Enter the User email of the person you want to add:");

            int receiverId = friendshipController.getUserIdByEmail(receiverEmail);
            boolean success;
            if (receiverId == -1){
                JOptionPane.showMessageDialog(this, "Invalid User Email.", "Error", JOptionPane.ERROR_MESSAGE);
            }else{
                if (checkValidRequest(userId, receiverId)) {
                    success = friendshipController.sendFriendRequest(userId, receiverId);
                } else {
                    success = false;
                }
                JOptionPane.showMessageDialog(this, success ? "Friend request sent!" : "Failed to send friend request.");
            }
        });

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

        JButton viewFriendLibraryButton = new JButton("View Friend Library");

        viewFriendLibraryButton.addActionListener((ActionEvent e) -> {
            int selectedRow = friendsTable.getSelectedRow();
            if (selectedRow != -1) {
                String email = (String) friendsTable.getValueAt(selectedRow, 1);
                int friendId = friendshipController.getUserIdByEmail(email);

                if (friendId != -1) {
                    try {
                        parentFrame.getContentPane().removeAll();
                        parentFrame.add(new FriendLibraryView(userId, userRole, friendId, parentFrame));
                        parentFrame.revalidate();
                        parentFrame.repaint();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error loading friend's library: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "User not found with email: " + email, "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a user to view their library.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });



        JButton deleteFriend = new JButton("Delete Friend");

        deleteFriend.addActionListener((ActionEvent e) ->{
            int selectedRow = friendsTable.getSelectedRow();
            if (selectedRow != -1) {
                String email = (String) friendsTable.getValueAt(selectedRow, 1);

                int friendId = friendshipController.getUserIdByEmail(email);

                if(friendshipController.deleteFriend(userId, friendId)){
                    JOptionPane.showMessageDialog(this, "Friend deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    refreshFriendsList(userId, (DefaultTableModel) friendsTable.getModel());
                }else{
                    JOptionPane.showMessageDialog(this, "Friend can't be deleted.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(sendRequestButton);
        bottomPanel.add(viewFriendLibraryButton);
        bottomPanel.add(deleteFriend);
        bottomPanel.add(backButton);
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
                if(checkValidRequest(userId, request.getId())){
                    tableModel.addRow(new Object[]{request.getId(), requester.getEmail(), request.getStatus()});
                }
            } else {
                tableModel.addRow(new Object[]{request.getId(), "Unknown User", request.getStatus()});
            }
        }
    }

    private boolean checkValidRequest(int userId, int receiverId){
        return friendshipController.checkValidRequest(userId, receiverId);
    }
}



