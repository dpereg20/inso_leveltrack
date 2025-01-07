package com.leveltrack.model;

public class Moderator extends UserBase{
    public Moderator(int id, String name, String email) {
        super(id, name, email, "Moderator");
    }

    @Override
    public void performAction() {
        System.out.println("Moderator: Moderating content.");
    }

    public void moderateReviews() {
        System.out.println("Moderator: Editing or deleting inappropriate reviews.");
    }

    public void handleFriendshipRequests() {
        System.out.println("Moderator: Accepting or rejecting friendship requests.");
    }
}

