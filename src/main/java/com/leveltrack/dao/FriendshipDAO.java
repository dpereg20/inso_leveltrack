package com.leveltrack.dao;

import com.leveltrack.model.Friendship;
import com.leveltrack.model.UserBase;

import java.util.List;

public interface FriendshipDAO {
    List<UserBase> searchUsers(String keyword);
    boolean sendFriendRequest(int requesterId, int receiverId);
    List<Friendship> getFriendRequests(int userId);
    boolean updateFriendRequestStatus(int friendshipId, String status);
}
