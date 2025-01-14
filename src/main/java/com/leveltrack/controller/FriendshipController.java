package com.leveltrack.controller;

import com.leveltrack.model.Friendship;
import com.leveltrack.model.UserBase;
import com.leveltrack.service.FriendshipService;

import java.util.List;

/**
 * The FriendshipController class handles operations related to friendships between users,
 * including sending and responding to friend requests, searching users, and managing friends.
 *
 * @author Level Track
 * @since 1.0
 */
public class FriendshipController {

    private final FriendshipService friendshipService;

    /**
     * Creates an instance of FriendshipController, initializing the FriendshipService.
     *
     * @throws Exception if there is an error initializing the service.
     */
    public FriendshipController() throws Exception {
        this.friendshipService = new FriendshipService();
    }

    /**
     * Searches for users based on a keyword.
     *
     * @param keyword The keyword to search for (e.g., name or email).
     * @return A list of {@code UserBase} objects matching the search criteria.
     */
    public List<UserBase> searchUsers(String keyword) {
        return friendshipService.searchUsers(keyword);
    }

    /**
     * Sends a friend request from one user to another.
     *
     * @param requesterId The ID of the user sending the friend request.
     * @param receiverId  The ID of the user receiving the friend request.
     * @return {@code true} if the request was sent successfully, {@code false} otherwise.
     */
    public boolean sendFriendRequest(int requesterId, int receiverId) {
        return friendshipService.sendFriendRequest(requesterId, receiverId);
    }

    /**
     * Checks if a friend request between two users is valid.
     *
     * @param requesterId The ID of the user sending the request.
     * @param receiverId  The ID of the user receiving the request.
     * @return {@code true} if the request is valid, {@code false} otherwise.
     */
    public boolean checkValidRequest(int requesterId, int receiverId) {
        return friendshipService.checkValidRequest(requesterId, receiverId);
    }

    /**
     * Retrieves the friend requests for a specific user.
     *
     * @param userId The ID of the user whose friend requests are being retrieved.
     * @return A list of {@code Friendship} objects representing the friend requests.
     */
    public List<Friendship> getFriendRequests(int userId) {
        return friendshipService.getFriendRequests(userId);
    }

    /**
     * Responds to a friend request.
     *
     * @param friendshipId The ID of the friendship request.
     * @param status       The new status of the friendship (e.g., "accepted", "rejected").
     * @return {@code true} if the response was successfully updated, {@code false} otherwise.
     */
    public boolean respondToFriendRequest(int friendshipId, String status) {
        return friendshipService.respondToFriendRequest(friendshipId, status);
    }

    /**
     * Retrieves the list of friends for a specific user.
     *
     * @param userId The ID of the user whose friends are being retrieved.
     * @return A list of {@code UserBase} objects representing the user's friends.
     */
    public List<UserBase> getFriends(int userId) {
        return friendshipService.getFriends(userId);
    }

    /**
     * Retrieves the user ID associated with a given email address.
     *
     * @param userEmail The email address of the user.
     * @return The ID of the user associated with the given email, or -1 if not found.
     */
    public int getUserIdByEmail(String userEmail) {
        return friendshipService.getUserIdByEmail(userEmail);
    }

    /**
     * Deletes a friend from a user's friend list.
     *
     * @param userId   The ID of the user removing the friend.
     * @param friendId The ID of the friend being removed.
     * @return {@code true} if the friend was successfully removed, {@code false} otherwise.
     */
    public boolean deleteFriend(int userId, int friendId) {
        return friendshipService.deleteFriend(userId, friendId);
    }
}
