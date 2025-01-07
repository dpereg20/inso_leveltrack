package com.leveltrack.service;

import com.leveltrack.dao.FriendshipDAO;
import com.leveltrack.dao.FriendshipDAOImpl;
import com.leveltrack.model.Friendship;
import com.leveltrack.model.UserBase;

import java.util.List;

public class FriendshipService {
    private final FriendshipDAO friendshipDAO;

    public FriendshipService() throws Exception {
        this.friendshipDAO = new FriendshipDAOImpl();
    }

    public List<UserBase> searchUsers(String keyword) {
        return friendshipDAO.searchUsers(keyword);
    }

    public boolean sendFriendRequest(int requesterId, int receiverId) {
        return friendshipDAO.sendFriendRequest(requesterId, receiverId);
    }

    public List<Friendship> getFriendRequests(int userId) {
        return friendshipDAO.getFriendRequests(userId);
    }

    public boolean respondToFriendRequest(int friendshipId, String status) {
        return friendshipDAO.updateFriendRequestStatus(friendshipId, status);
    }
}
