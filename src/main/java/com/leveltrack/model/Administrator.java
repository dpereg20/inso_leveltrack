package com.leveltrack.model;

public class Administrator extends UserBase {
    public Administrator(int id, String name, String email) {
        super(id, name, email, "ADMINISTRATOR");
    }

    @Override
    public void performAction() {
        System.out.println("Administrator privileges granted.");
    }
}
