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
            stmt.setString(1,  keyword);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(createUserInstance(
                        rs.getString("role"),
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email")
                ));
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
    public List<UserBase> getFriends(int userId) {
        List<UserBase> friends = new ArrayList<>();
        String query = QueryLoader.getQuery("friendship.getFriends");
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, userId);
            stmt.setInt(3, userId); // To exclude the current user from the friends list
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String role = rs.getString("role");
                UserBase user = createUserInstance(
                        role,
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email")
                );
                friends.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friends;
    }


    @Override
    public boolean sendFriendRequest(int requesterId, int receiverId) {
        if(requesterId == receiverId){
            return false;
        }
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

    public boolean checkValidRequest(int requesterId, int receiverId){
        String checkFriendshipQuery = QueryLoader.getQuery("friendship.checkFriendship");
        try (PreparedStatement checkFriendshipStmt = connection.prepareStatement(checkFriendshipQuery)) {
            checkFriendshipStmt.setInt(1, requesterId);
            checkFriendshipStmt.setInt(2, receiverId);
            checkFriendshipStmt.setInt(3, receiverId);
            checkFriendshipStmt.setInt(4, requesterId);
            try (ResultSet rs = checkFriendshipStmt.executeQuery()) {
                if (rs.next()) {
                    return false; // Ya son amigos
                }
            }

        String checkPendingRequestQuery = QueryLoader.getQuery("friendship.checkPendingRequest");
        try (PreparedStatement checkPendingRequestStmt = connection.prepareStatement(checkPendingRequestQuery)) {
            checkPendingRequestStmt.setInt(1, requesterId);
            checkPendingRequestStmt.setInt(2, receiverId);
            checkPendingRequestStmt.setInt(3, receiverId);
            checkPendingRequestStmt.setInt(4, requesterId);
            try (ResultSet rs = checkPendingRequestStmt.executeQuery()) {
                if (rs.next()) {
                    return false; // Ya hay una solicitud pendiente
                }
            }
        }

        // Si no son amigos y no hay solicitudes pendientes, se puede enviar la solicitud
        return true;
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Error checking valid request", e);
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

    @Override
    public int getUserIdByEmail(String email) {
        String query = QueryLoader.getQuery("friendship.getUserIdByUserEmail");  // Consulta SQL
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);  // Establecer el email como parámetro en la consulta
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");  // Retorna el userId si se encuentra el email
            } else {
                return -1;  // Si no se encuentra el usuario, retorna -1
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;  // Retorna -1 en caso de error en la consulta
        }
    }

    @Override
    public boolean deleteFriend(int userId, int friendId){
        String query = QueryLoader.getQuery("friendship.deleteFriend");
        try(PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setInt(1, userId);
            stmt.setInt(2, friendId);
            stmt.setInt(3, friendId);
            stmt.setInt(4, userId);

            return stmt.executeUpdate() > 0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}

