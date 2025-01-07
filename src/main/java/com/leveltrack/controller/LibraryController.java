package com.leveltrack.controller;

import com.leveltrack.model.Game;
import com.leveltrack.service.LibraryService;

import java.util.List;

public class LibraryController {
    private final LibraryService libraryService;

    public LibraryController() throws Exception {
        this.libraryService = new LibraryService();
    }

    public List<Game> getAllGames() {
        return libraryService.getAllGames();
    }

    public List<Game> getGamesByGenre(String genre) {
        return libraryService.getGamesByGenre(genre);
    }

    public List<Game> getUserLibraryGames(int userId) {
        return libraryService.getUserLibraryGames(userId);
    }

    public boolean addGameToLibrary(int userId, int gameId) {
        return libraryService.addGameToLibrary(userId, gameId);
    }
}