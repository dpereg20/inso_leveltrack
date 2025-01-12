package com.leveltrack.controller;

import com.leveltrack.model.Game;
import com.leveltrack.service.LibraryService;

import java.util.List;

public class LibraryController {
    private final LibraryService libraryService;

    public LibraryController() throws Exception {
        this.libraryService = new LibraryService();
    }

    public List<Game> getGamesByUserId(int userId) {
        return libraryService.getGamesByUserId(userId);
    }

    public List<Game> getGamesByGenre(int userId, String genre){
        return libraryService.getGamesByGenre(userId, genre);
    }

    public boolean addGameToLibrary(int libraryId, String gameName) throws Exception {
        Game game = libraryService.getGameByName(gameName);
        if (game == null) {
            throw new Exception("Game not found in the database.");
        }
        boolean alreadyInLibrary = libraryService.isGameInLibrary(libraryId, game.getId());
        if (alreadyInLibrary) {
            throw new Exception("Game is already in the library.");
        }
        return libraryService.addGameToLibrary(libraryId, game.getId(), "Available");
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


