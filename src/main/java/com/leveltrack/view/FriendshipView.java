package com.leveltrack.view;

import com.leveltrack.controller.FriendshipController;
import com.leveltrack.model.UserBase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class FriendshipView extends JPanel {
    private final FriendshipController friendshipController;

    public FriendshipView(int userId) {
        setLayout(new BorderLayout());

        try {
            friendshipController = new FriendshipController();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize FriendshipController", e);
        }

        JTextField searchField = new JTextField();
        JButton searchButton = new JButton("Search");
        JTextArea resultsArea = new JTextArea();
        resultsArea.setEditable(false);

        searchButton.addActionListener((ActionEvent e) -> {
            String keyword = searchField.getText().trim();
            List<UserBase> users = friendshipController.searchUsers(keyword);
            StringBuilder sb = new StringBuilder();
            for (UserBase user : users) {
                sb.append(user.getId()).append(" - ").append(user.getName()).append("\n");
            }
            resultsArea.setText(sb.toString());
        });

        JButton sendRequestButton = new JButton("Send Friend Request");
        sendRequestButton.addActionListener((ActionEvent e) -> {
            String receiverIdInput = JOptionPane.showInputDialog("Enter User ID to send a friend request:");
            try {
                int receiverId = Integer.parseInt(receiverIdInput);
                boolean success = friendshipController.sendFriendRequest(userId, receiverId);
                JOptionPane.showMessageDialog(this, success ? "Friend request sent successfully!" : "Failed to send request.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid User ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(searchField, BorderLayout.NORTH);
        add(new JScrollPane(resultsArea), BorderLayout.CENTER);
        add(sendRequestButton, BorderLayout.SOUTH);
    }
}