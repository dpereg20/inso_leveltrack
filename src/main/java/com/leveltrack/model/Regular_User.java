package com.leveltrack.model;

public class Regular_User extends UserBase {
    public Regular_User(int id, String name, String email) {
        super(id, name, email, "Regular_User");
    }

    @Override
    public void performAction() {
        System.out.println("Regular User: Managing personal library.");
    }

    public void manageLibrary() {
        System.out.println("Regular User: Adding or removing games.");
    }

    public void writeReview() {
        System.out.println("Regular User: Writing a review for owned games.");
    }

    public void sendFriendRequest() {
        System.out.println("Regular User: Sending a friendship request.");
    }
}
