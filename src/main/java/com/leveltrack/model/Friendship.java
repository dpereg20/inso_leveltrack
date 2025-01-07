package com.leveltrack.model;

public class Friendship {
    private int id;
    private int requesterId;
    private int receiverId;
    private String status;

    public Friendship(int id, int requesterId, int receiverId, String status) {
        this.id = id;
        this.requesterId = requesterId;
        this.receiverId = receiverId;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getRequesterId() {
        return requesterId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Friendship ID: " + id + ", Requester ID: " + requesterId + ", Receiver ID: " + receiverId + ", Status: " + status;
    }
}

