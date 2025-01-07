package com.leveltrack.controller;

import com.leveltrack.service.UserService;

public class AdminController {
    private final UserService userService;

    public AdminController() throws Exception {
        this.userService = new UserService();
    }

    public void assignUserRole(int adminId, int userId, String newRole) {
        try {
            // Validate admin permissions (optional)
            if (!userService.isAdmin(adminId)) {
                System.out.println("Permission denied. Only administrators can modify roles.");
                return;
            }

            // Update the role
            if (userService.updateUserRole(userId, newRole)) {
                System.out.println("Role updated successfully for user ID " + userId);
            } else {
                System.out.println("Failed to update role for user ID " + userId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error updating role: " + e.getMessage());
        }
    }
}

