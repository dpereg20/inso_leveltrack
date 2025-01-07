package com.leveltrack.model;

import com.leveltrack.service.UserService;

public class Administrator extends UserBase {
    public Administrator(int id, String name, String email) {
        super(id, name, email, "Administrator");
    }

    @Override
    public void performAction() {
        System.out.println("Administrator: Managing users and libraries.");
    }

    public void manageUserAccounts() {
        System.out.println("Administrator: Creating, updating, or deleting users.");
    }

    /**
     * Assigns a role to a user.
     * @param userId The ID of the user to update.
     * @param newRole The new role to assign.
     */
    public void assignRole(int userId, String newRole, UserService userService) {
        if (!userService.isValidRole(newRole)) {
            throw new IllegalArgumentException("Invalid role: " + newRole);
        }
        userService.updateUserRole(userId, newRole);
        System.out.println("User with ID " + userId + " is now a " + newRole);
    }






}
