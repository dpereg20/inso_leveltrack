package com.leveltrack.controller;

import com.leveltrack.model.Game;
import com.leveltrack.model.UserBase;
import com.leveltrack.service.GameService;
import com.leveltrack.service.UserService;

import java.util.List;

public class AdminController {
    private final UserService userService;
    private final GameService gameService;

    public AdminController() throws Exception {
        this.userService = new UserService();
        this.gameService = new GameService();
    }

    public void assignUserRole(int adminId, int userId, String newRole) {
        try {
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

    public boolean addGame(Game game) {
        return gameService.addGame(game);
    }

    public boolean updateGame(Game game) {
        return gameService.updateGame(game);
    }

    public boolean deleteGame(int gameId) {
        return gameService.deleteGame(gameId);
    }

    public boolean deleteUser(int userId) {
        return userService.deleteUser(userId);
    }

    public List<Game> getAllGames() {
        try {
            return gameService.getAllGames();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<UserBase> getAllUsers() {
        try {
            return userService.getAllUsers();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public UserBase findUserByName(String selectedName) {
        try {
            List<UserBase> users = userService.getAllUsers();
            for (UserBase user : users) {
                if (user.getName().equalsIgnoreCase(selectedName)) {
                    return user;
                }
            }
            System.out.println("User not found with name: " + selectedName);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Game findGameByName(String selectedName) {
        try {
            List<Game> games = gameService.getAllGames();
            for (Game game : games) {
                if (game.getName().equalsIgnoreCase(selectedName)) {
                    return game;
                }
            }
            System.out.println("Game not found with name: " + selectedName);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}


