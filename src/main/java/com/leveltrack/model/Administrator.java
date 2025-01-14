package com.leveltrack.model;

import com.leveltrack.service.UserService;

public class Administrator extends UserBase {

    /**
     * Constructor for the Administrator class.
     *
     * @param id    The unique ID of the administrator.
     * @param name  The name of the administrator.
     * @param email The email of the administrator.
     */
    public Administrator(int id, String name, String email) {
        super(id, name, email, "Administrator");
    }

    /**
     *
     * Overrides the abstract method from UserBase.
     * Prints a message indicating the general actions an administrator can perform.
     */
    @Override
    public void performAction() {
        System.out.println("Administrator: Managing users and libraries.");
    }

    /**
     *
     * Prints a message indicating that the administrator is managing user accounts.
     *
     */
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
