package com.leveltrack.model;

public class Regular_User extends UserBase {
    public Regular_User(int id, String name, String email) {
        super(id, name, email, "REGULAR_USER");
    }

    @Override
    public void performAction() {
        System.out.println("Regular user privileges granted.");
    }
}
