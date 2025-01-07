package com.leveltrack.controller;

import com.leveltrack.model.Game;
import com.leveltrack.service.LibraryService;

import java.util.List;

public class LibraryController {
    private final LibraryService libraryService;

    public LibraryController() throws Exception {
        this.libraryService = new LibraryService();
    }

    /**
     * Retrieve all games in the user's library.
     *
     * @param userId The ID of the user.
     * @return A list of games in the library.
     */
    public List<Game> getGames(int userId) {
        return libraryService.getGamesByUserId(userId);
    }

    /**
     * Add a new game to the user's library.
     *
     * @param libraryId The ID of the user's library.
     * @param game  	The game to add.
     * @return True if the game was added successfully, false otherwise.
     */
    public boolean addGame(int libraryId, Game game) {
        if (game == null || game.getName().isEmpty()) {
            throw new IllegalArgumentException("Game cannot be null, and its name cannot be empty.");
        }

        return libraryService.addGameToLibrary(libraryId, game);
    }

    /**
     * Remove a game from the user's library.
     *
     * @param libraryId The ID of the user's library.
     * @param gameId	The ID of the game to remove.
     * @return True if the game was removed successfully, false otherwise.
     */
    public boolean removeGame(int libraryId, int gameId) {
        if (gameId <= 0) {
            throw new IllegalArgumentException("Game ID must be greater than 0.");
        }

        return libraryService.removeGameFromLibrary(libraryId, gameId);
    }

    /**
     * Change the state of a game in the user's library.
     *
     * @param gameId  The ID of the game.
     * @param newState The new state of the game.
     * @return True if the game state was updated successfully, false otherwise.
     */
    public boolean changeGameState(int gameId, String newState) {
        if (gameId <= 0 || newState.isEmpty()) {
            throw new IllegalArgumentException("Game ID must be greater than 0, and new state cannot be empty.");
        }

        return libraryService.updateGameState(gameId, newState);
    }
}











