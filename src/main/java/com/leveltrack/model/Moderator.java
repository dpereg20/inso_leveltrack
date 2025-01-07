package com.leveltrack.model;

public class Moderator extends UserBase {
    public Moderator(int id, String name, String email) {
        super(id, name, email, "MODERATOR");
    }

    @Override
    public void performAction() {
        System.out.println("Moderator privileges granted.");
    }
}
