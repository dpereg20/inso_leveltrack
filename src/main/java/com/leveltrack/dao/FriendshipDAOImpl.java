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

/**
 * Implementation of the FriendshipDAO interface for managing friendship-related data
 * in the "Level Track" application.
 * This class interacts with the database to perform CRUD operations for friendship management.
 *
 * @author Level Track
 * @since 1.0
 */

public class FriendshipDAOImpl implements FriendshipDAO {
    private final Connection connection;

    /**
     * Constructs a new FriendshipDAOImpl and initializes the database connection.
     *
     * @throws Exception if there is an issue initializing the database connection.
     */

    public FriendshipDAOImpl() throws Exception {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    /**
     * Searches for users in the system whose details match the provided keyword.
     *
     * @param keyword The search term to match user names or emails.
     * @return A list of UserBase objects that match the search criteria.
     */
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

    /**
     * Creates an instance of the appropriate UserBase subclass based on the user's role.
     *
     * @param role  The role of the user.
     * @param id    The user ID.
     * @param name  The user's name.
     * @param email The user's email.
     * @return An instance of Administrator, Moderator, or Regular_User.
     */
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

    /**
     * Retrieves the friends of a specific user.
     *
     * @param userId The ID of the user.
     * @return A list of UserBase objects representing the user's friends.
     */
    @Override
    public List<UserBase> getFriends(int userId) {
        List<UserBase> friends = new ArrayList<>();
        String query = QueryLoader.getQuery("friendship.getFriends");
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, userId);
            stmt.setInt(3, userId);
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

    /**
     * Sends a friend request from one user to another.
     *
     * @param requesterId The ID of the user sending the request.
     * @param receiverId  The ID of the user receiving the request.
     * @return {@code true} if the request was successfully sent; {@code false} otherwise.
     */
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

    /**
     * Validates whether a friend request can be sent between two users.
     *
     * @param requesterId The ID of the requesting user.
     * @param receiverId  The ID of the receiving user.
     * @return {@code true} if the request is valid; {@code false} otherwise.
     */
    public boolean checkValidRequest(int requesterId, int receiverId){
        String checkFriendshipQuery = QueryLoader.getQuery("friendship.checkFriendship");
        try (PreparedStatement checkFriendshipStmt = connection.prepareStatement(checkFriendshipQuery)) {
            checkFriendshipStmt.setInt(1, requesterId);
            checkFriendshipStmt.setInt(2, receiverId);
            checkFriendshipStmt.setInt(3, receiverId);
            checkFriendshipStmt.setInt(4, requesterId);
            try (ResultSet rs = checkFriendshipStmt.executeQuery()) {
                if (rs.next()) {
                    return false;
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
                    return false;
                }
            }
        }

        return true;
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Error checking valid request", e);
    }
    }

    /**
     * Receives a friend request from one user to another.
     *
     * @param userId The ID of the user sending the request.
     * @return A list of the friendship request received by the user with the Id userId
     */
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
        String query = QueryLoader.getQuery("friendship.getUserIdByUserEmail");
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            } else {
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
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

