package com.leveltrack.view;

import com.leveltrack.controller.FriendshipController;
import com.leveltrack.model.Friendship;
import com.leveltrack.model.UserBase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class FriendshipView extends JPanel {
    private final FriendshipController friendshipController;

    public FriendshipView(int userId) throws Exception {
        setLayout(new BorderLayout());

        try {
            friendshipController = new FriendshipController();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize FriendshipController", e);
        }

        // Friends Section
        JPanel friendsPanel = new JPanel(new BorderLayout());
        JTextArea friendsArea = new JTextArea();
        friendsArea.setEditable(false);
        refreshFriendsList(userId, friendsArea);

        JButton refreshFriendsButton = new JButton("Refresh Friends");
        refreshFriendsButton.addActionListener((ActionEvent e) -> {
            try {
                refreshFriendsList(userId, friendsArea);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        friendsPanel.add(new JScrollPane(friendsArea), BorderLayout.CENTER);
        friendsPanel.add(refreshFriendsButton, BorderLayout.SOUTH);
        add(friendsPanel, BorderLayout.WEST);

        // Pending Requests Section
        JPanel pendingRequestsPanel = new JPanel(new BorderLayout());
        JTextArea pendingRequestsArea = new JTextArea();
        pendingRequestsArea.setEditable(false);
        refreshPendingRequests(userId, pendingRequestsArea);

        JButton acceptRequestButton = new JButton("Accept");
        JButton rejectRequestButton = new JButton("Reject");

        acceptRequestButton.addActionListener((ActionEvent e) -> {
            String requestIdInput = JOptionPane.showInputDialog("Enter Friendship Request ID to Accept:");
            try {
                int requestId = Integer.parseInt(requestIdInput);
                boolean success = friendshipController.respondToFriendRequest(requestId, "Accepted");
                JOptionPane.showMessageDialog(this, success ? "Friend request accepted!" : "Failed to accept request.");
                refreshPendingRequests(userId, pendingRequestsArea);
                refreshFriendsList(userId, friendsArea);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid Request ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        rejectRequestButton.addActionListener((ActionEvent e) -> {
            String requestIdInput = JOptionPane.showInputDialog("Enter Friendship Request ID to Reject:");
            try {
                int requestId = Integer.parseInt(requestIdInput);
                boolean success = friendshipController.respondToFriendRequest(requestId, "Rejected");
                JOptionPane.showMessageDialog(this, success ? "Friend request rejected!" : "Failed to reject request.");
                refreshPendingRequests(userId, pendingRequestsArea);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Request ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2));
        buttonsPanel.add(acceptRequestButton);
        buttonsPanel.add(rejectRequestButton);

        pendingRequestsPanel.add(new JScrollPane(pendingRequestsArea), BorderLayout.CENTER);
        pendingRequestsPanel.add(buttonsPanel, BorderLayout.SOUTH);
        add(pendingRequestsPanel, BorderLayout.CENTER);
    }

    private void refreshFriendsList(int userId, JTextArea friendsArea) throws Exception {
        List<UserBase> friends = friendshipController.getFriends(userId);
        StringBuilder sb = new StringBuilder();
        for (UserBase friend : friends) {
            sb.append(friend.getId()).append(" - ").append(friend.getName()).append("\n");
        }
        friendsArea.setText(sb.toString());
    }

    private void refreshPendingRequests(int userId, JTextArea pendingRequestsArea) {
        List<Friendship> pendingRequests = friendshipController.getFriendRequests(userId);
        StringBuilder sb = new StringBuilder();
        for (Friendship request : pendingRequests) {
            sb.append("Request ID: ").append(request.getId())
                    .append(", Requester ID: ").append(request.getRequesterId())
                    .append(", Status: ").append(request.getStatus())
                    .append("\n");
        }
        pendingRequestsArea.setText(sb.toString());
    }
}
