package com.leveltrack.model;

public class Regular_User extends UserBase {

    /**
     * Constructor for the Regular_User class.
     *
     * @param id    The unique ID of the regular user.
     * @param name  The name of the regular user.
     * @param email The email of the regular user.
     */
    public Regular_User(int id, String name, String email) {
        super(id, name, email, "Regular_User");
    }

    /**
     * Performs the main action for the Regular_User role.
     * Prints a message indicating the user's task of managing their personal library.
     */
    @Override
    public void performAction() {
        System.out.println("Regular User: Managing personal library.");
    }

    /**
     * Allows the regular user to manage their personal library.
     * Prints a message indicating the user is adding or removing games.
     */
    public void manageLibrary() {
        System.out.println("Regular User: Adding or removing games.");
    }

    /**
     * Allows the regular user to write reviews for owned games.
     * Prints a message indicating the user is writing a review.
     */
    public void writeReview() {
        System.out.println("Regular User: Writing a review for owned games.");
    }

    /**
     * Allows the regular user to send a friendship request to another user.
     * Prints a message indicating the user is sending a friendship request.
     */
    public void sendFriendRequest() {
        System.out.println("Regular User: Sending a friendship request.");
    }
}
