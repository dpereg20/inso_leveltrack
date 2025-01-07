package com.leveltrack.service;

import com.leveltrack.dao.LibraryDAO;
import com.leveltrack.dao.LibraryDAOImpl;
import com.leveltrack.model.Game;

import java.util.List;

public class LibraryService {
    private final LibraryDAO libraryDAO;

    public LibraryService() throws Exception {
        this.libraryDAO = new LibraryDAOImpl();
    }

    public List<Game> getAllGames() {
        return libraryDAO.getAllGames();
    }

    public List<Game> getGamesByGenre(String genre) {
        return libraryDAO.getGamesByGenre(genre);
    }

    public List<Game> getUserLibraryGames(int userId) {
        return libraryDAO.getUserLibraryGames(userId);
    }

    public boolean isGameInLibrary(int userId, int gameId) {
        return libraryDAO.isGameInLibrary(userId, gameId);
    }

    public boolean addGameToLibrary(int userId, int gameId) {
        return libraryDAO.addGameToLibrary(userId, gameId);
    }
}