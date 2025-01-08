package com.leveltrack.controller;

import com.leveltrack.model.Game;
import com.leveltrack.service.LibraryService;

import java.util.List;

public class LibraryController {
    private final LibraryService libraryService;

    public LibraryController() throws Exception {
        this.libraryService = new LibraryService();
    }

    public List<Game> getGames(int userId) {
        return libraryService.getGamesByUserId(userId);
    }

    public boolean addGameToLibrary(int userId, int gameId) throws Exception {
        int libraryId = libraryService.getLibraryIdByUserId(userId); // Fetch library ID for the user
        return libraryService.addGameToLibrary(libraryId, gameId, "Available");
    }


    public boolean removeGame(int userId, int gameId) {
        int libraryId = libraryService.getLibraryIdByUserId(userId);
        return libraryService.removeGameFromLibrary(libraryId, gameId);
    }

    public boolean changeGameState(int gameId, String newState) {
        return libraryService.updateGameState(gameId, newState);
    }

    public List<Game> getAvailableGames() {
        return libraryService.getAllGamesFromDatabase();
    }


}


