package com.leveltrack.controller;

import com.leveltrack.model.Game;
import com.leveltrack.service.LibraryService;

import java.util.List;

/**
 * The LibraryController class manages the interactions between the library services
 * and the presentation layer, handling operations related to a user's game library.
 *
 * This class is part of the "Level Track" project.
 *
 * @author Level Track
 * @since 1.0
 */
public class LibraryController {

    private final LibraryService libraryService;

    /**
     * Constructs a new LibraryController, initializing the LibraryService.
     *
     * @throws Exception if there is an issue initializing the LibraryService.
     */
    public LibraryController() throws Exception {
        this.libraryService = new LibraryService();
    }

    /**
     * Retrieves a list of games owned by a specific user.
     *
     * @param userId The ID of the user.
     * @return A list of {@code Game} objects owned by the user.
     */
    public List<Game> getGamesByUserId(int userId) {
        return libraryService.getGamesByUserId(userId);
    }

    /**
     * Retrieves a list of games by a specific genre for a given user.
     *
     * @param userId The ID of the user.
     * @param genre  The genre to filter by.
     * @return A list of {@code Game} objects matching the genre for the user.
     */
    public List<Game> getGamesByGenreUser(int userId, String genre) {
        return libraryService.getGamesByGenreUser(userId, genre);
    }

    /**
     * Adds a game to a user's library.
     *
     * @param userId   The ID of the user.
     * @param gameName The name of the game to add.
     * @return {@code true} if the game was added successfully, {@code false} if it was already in the library.
     * @throws Exception if the game is not found in the database.
     */
    public boolean addGameToLibrary(int userId, String gameName) throws Exception {
        Game game = libraryService.getGameByName(gameName);
        if (game == null) {
            throw new Exception("Game not found in the database.");
        }
        boolean alreadyInLibrary = libraryService.isGameInLibrary(userId, game.getId());
        if (alreadyInLibrary) {
            return false;
        }
        return libraryService.addGameToLibrary(userId, game.getId(), "Available");
    }

    /**
     * Removes a game from a user's library.
     *
     * @param userId The ID of the user.
     * @param gameId The ID of the game to remove.
     * @return {@code true} if the game was removed successfully, {@code false} otherwise.
     */
    public boolean removeGame(int userId, int gameId) {
        int libraryId = libraryService.getLibraryIdByUserId(userId);
        return libraryService.removeGameFromLibrary(libraryId, gameId);
    }

    /**
     * Retrieves a list of all games in the library.
     *
     * @return A list of all {@code Game} objects in the library.
     */
    public List<Game> getAllGames() {
        return libraryService.getAllGames();
    }

    /**
     * Searches for games by their name.
     *
     * @param keyword The keyword to search for.
     * @return A list of {@code Game} objects matching the keyword.
     */
    public List<Game> searchGamesByName(String keyword) {
        return libraryService.searchGamesByName(keyword);
    }

    /**
     * Retrieves a list of games by genre.
     *
     * @param genre The genre to filter by.
     * @return A list of {@code Game} objects matching the genre.
     */
    public List<Game> getGamesByGenre(String genre) {
        return libraryService.getGamesByGenre(genre);
    }

    /**
     * Retrieves a list of all genres available in the library.
     *
     * @return A list of genres as {@code String} values.
     */
    public List<String> getAllGenres() {
        return libraryService.getAllGenres();
    }

    /**
     * Updates the state of a game for a user.
     *
     * @param gameId   The ID of the game.
     * @param userId   The ID of the user.
     * @param newState The new state to set (e.g., "Available", "Completed").
     * @return {@code true} if the state was updated successfully, {@code false} otherwise.
     */
    public boolean updateGameState(int gameId, int userId, String newState) {
        return libraryService.updateGameState(gameId, userId, newState);
    }

    /**
     * Updates the score of a game for a user.
     *
     * @param gameId The ID of the game.
     * @param userId The ID of the user.
     * @param score  The new score to set.
     * @return {@code true} if the score was updated successfully, {@code false} otherwise.
     */
    public boolean updateGameScore(int gameId, int userId, int score) {
        System.out.println("Updating score for gameId: " + gameId + ", userId: " + userId + " to " + score);
        return libraryService.updateGameScore(gameId, userId, score);
    }

    /**
     * Retrieves the score of a game for a specific user.
     *
     * @param gameId The ID of the game.
     * @param userId The ID of the user.
     * @return The score of the game for the user, or -1 if not found.
     */
    public int getGameScore(int gameId, int userId) {
        return libraryService.getGameScore(gameId, userId);
    }

    public Game findGameByName(String selectedName) {
        try {
            List<Game> games = libraryService.getAllGames();
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
