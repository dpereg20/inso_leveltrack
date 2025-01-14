package com.leveltrack.controller;

import com.leveltrack.model.Game;
import com.leveltrack.model.UserBase;
import com.leveltrack.service.GameService;
import com.leveltrack.service.UserService;

import java.util.List;

/**
 * The AdminController class handles administrative operations such as managing users and games.
 * <p>
 * This class provides methods to assign roles to users, add, update, and delete games, as well as
 * retrieve information about games and users.
 *
 * @author Level Track
 * @since 1.0
 */
public class AdminController {

    private final UserService userService;
    private final GameService gameService;

    /**
     * Creates an instance of AdminController, initializing the required services.
     *
     * @throws Exception if there is an error initializing the services.
     */
    public AdminController() throws Exception {
        this.userService = new UserService();
        this.gameService = new GameService();
    }

    /**
     * Assigns a new role to a user.
     *
     * @param adminId The ID of the admin making the change.
     * @param userId  The ID of the user whose role is being updated.
     * @param newRole The new role to be assigned to the user.
     */
    public void assignUserRole(int adminId, int userId, String newRole) {
        try {
            userService.updateUserRole(userId, newRole);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new game to the database.
     *
     * @param game The game to be added.
     * @return {@code true} if the game was added successfully, {@code false} if the game already exists.
     */
    public boolean addGame(Game game) {
        if (gameService.isGameInDatabase(game.getName())) {
            return false;
        } else {
            return gameService.addGame(game);
        }
    }

    /**
     * Updates an existing game in the database.
     *
     * @param game The game with updated details.
     * @return {@code true} if the game was updated successfully, {@code false} otherwise.
     */
    public boolean updateGame(Game game) {
        return gameService.updateGame(game);
    }

    /**
     * Deletes a game from the database.
     *
     * @param gameId The ID of the game to be deleted.
     * @return {@code true} if the game was deleted successfully, {@code false} otherwise.
     */
    public boolean deleteGame(int gameId) {
        return gameService.deleteGame(gameId);
    }

    /**
     * Deletes a user from the database.
     *
     * @param userId The ID of the user to be deleted.
     * @return {@code true} if the user was deleted successfully, {@code false} otherwise.
     */
    public boolean deleteUser(int userId) {
        return userService.deleteUser(userId);
    }

    /**
     * Retrieves a list of all games from the database.
     *
     * @return A list of all games. Returns {@code null} if an error occurs.
     */
    public List<Game> getAllGames() {
        try {
            return gameService.getAllGames();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieves a list of all users from the database.
     *
     * @return A list of all users. Returns {@code null} if an error occurs.
     */
    public List<UserBase> getAllUsers() {
        try {
            return userService.getAllUsers();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Finds a user by their name.
     *
     * @param selectedName The name of the user to find.
     * @return The {@code UserBase} object if the user is found, {@code null} otherwise.
     */
    public UserBase findUserByName(String selectedName) {
        try {
            List<UserBase> users = userService.getAllUsers();
            for (UserBase user : users) {
                if (user.getName().equalsIgnoreCase(selectedName)) {
                    return user;
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Finds a game by its name.
     *
     * @param selectedName The name of the game to find.
     * @return The {@code Game} object if the game is found, {@code null} otherwise.
     */
    public Game findGameByName(String selectedName) {
        try {
            List<Game> games = gameService.getAllGames();
            for (Game game : games) {
                if (game.getName().equalsIgnoreCase(selectedName)) {
                    return game;
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
