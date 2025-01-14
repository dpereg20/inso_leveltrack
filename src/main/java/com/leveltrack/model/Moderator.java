package com.leveltrack.model;

public class Moderator extends UserBase{

    /**
     * Constructor for the Moderator class.
     *
     * @param id    The unique ID of the moderator.
     * @param name  The name of the moderator.
     * @param email The email of the moderator.
     */
    public Moderator(int id, String name, String email) {
        super(id, name, email, "Moderator");
    }

    /**
     * Performs the main action for the Moderator role.
     * Prints a message indicating the moderator's task of moderating content.
     */
    @Override
    public void performAction() {
        System.out.println("Moderator: Moderating content.");
    }

    /**
     * Moderates user reviews.
     * Prints a message indicating that the moderator is editing or deleting inappropriate reviews.
     */
    public void moderateReviews() {
        System.out.println("Moderator: Editing or deleting inappropriate reviews.");
    }

    /**
     * Handles friendship requests between users.
     * Prints a message indicating that the moderator is accepting or rejecting friendship requests.
     */
    public void handleFriendshipRequests() {
        System.out.println("Moderator: Accepting or rejecting friendship requests.");
    }
}

