package com.leveltrack.model;

public class Friendship {
    private int id;
    private int requesterId;
    private int receiverId;
    private String status;

    /**
     * Constructor for the Friendship class.
     *
     * @param id          The unique ID of the friendship.
     * @param requesterId The ID of the user who sent the friendship request.
     * @param receiverId  The ID of the user who received the friendship request.
     * @param status      The current status of the friendship.
     */
    public Friendship(int id, int requesterId, int receiverId, String status) {
        this.id = id;
        this.requesterId = requesterId;
        this.receiverId = receiverId;
        this.status = status;
    }

    /**
     * Retrieves the unique ID of the friendship.
     *
     * @return The friendship ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the ID of the user who sent the friendship request.
     *
     * @return The requester's ID.
     */
    public int getRequesterId() {
        return requesterId;
    }

    /**
     * Retrieves the ID of the user who received the friendship request.
     *
     * @return The receiver's ID.
     */
    public int getReceiverId() {
        return receiverId;
    }

    /**
     * Retrieves the current status of the friendship.
     *
     * @return The status of the friendship.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Updates the current status of the friendship.
     *
     * @param status The new status to set.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Provides a string representation of the friendship.
     *
     * @return A formatted string containing the friendship details.
     *
     */
    @Override
    public String toString() {
        return "Friendship ID: " + id + ", Requester ID: " + requesterId + ", Receiver ID: " + receiverId + ", Status: " + status;
    }
}

