package com.leveltrack.controller;

import com.leveltrack.model.Friendship;
import com.leveltrack.model.UserBase;
import com.leveltrack.service.FriendshipService;

import java.util.List;

public class FriendshipController {
    private final FriendshipService friendshipService;

    public FriendshipController() throws Exception {
        this.friendshipService = new FriendshipService();
    }

    public List<UserBase> searchUsers(String keyword) {
        return friendshipService.searchUsers(keyword);
    }

    public boolean sendFriendRequest(int requesterId, int receiverId) {
        return friendshipService.sendFriendRequest(requesterId, receiverId);
    }

    public boolean checkValidRequest(int requesterId, int receiverId) {
        return friendshipService.checkValidRequest(requesterId, receiverId);
    }

    public List<Friendship> getFriendRequests(int userId) {
        return friendshipService.getFriendRequests(userId);
    }

    public boolean respondToFriendRequest(int friendshipId, String status) {
        return friendshipService.respondToFriendRequest(friendshipId, status);
    }

    public List<UserBase> getFriends(int userId) {
        return friendshipService.getFriends(userId);
    }

    public int getUserIdByEmail(String userEmail) {
        return friendshipService.getUserIdByEmail(userEmail);
    }

    public boolean deleteFriend(int userId, int friendId){
        return friendshipService.deleteFriend(userId, friendId);
    }

}


