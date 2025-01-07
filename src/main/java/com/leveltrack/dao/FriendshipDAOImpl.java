package com.leveltrack.dao;

import com.leveltrack.model.*;
import com.leveltrack.util.DatabaseConnection;
import com.leveltrack.util.QueryLoader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FriendshipDAOImpl implements FriendshipDAO {
    private final Connection connection;

    public FriendshipDAOImpl() throws Exception {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public List<UserBase> searchUsers(String keyword) {
        List<UserBase> users = new ArrayList<>();
        String query = QueryLoader.getQuery("friendship.searchUsers");
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String role = rs.getString("role");
                UserBase user = createUserInstance(
                        role,
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }


    private UserBase createUserInstance(String role, int id, String name, String email) {
        switch (role) {
            case "ADMINISTRATOR":
                return new Administrator(id, name, email);
            case "MODERATOR":
                return new Moderator(id, name, email);
            case "REGULAR_USER":
            default:
                return new Regular_User(id, name, email);
        }
    }




    @Override
    public boolean sendFriendRequest(int requesterId, int receiverId) {
        String query = QueryLoader.getQuery("friendship.sendRequest");
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, requesterId);
            stmt.setInt(2, receiverId);
            stmt.setString(3, "Pending");
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Friendship> getFriendRequests(int userId) {
        List<Friendship> requests = new ArrayList<>();
        String query = QueryLoader.getQuery("friendship.getRequests");
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                requests.add(new Friendship(
                        rs.getInt("id"),
                        rs.getInt("requester_id"),
                        rs.getInt("receiver_id"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }

    @Override
    public boolean updateFriendRequestStatus(int friendshipId, String status) {
        String query = QueryLoader.getQuery("friendship.updateStatus");
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, status);
            stmt.setInt(2, friendshipId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

