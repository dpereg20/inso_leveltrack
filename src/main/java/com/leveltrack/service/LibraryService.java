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

    public List<Game> getGamesByUserId(int userId) {
        return libraryDAO.getGamesByUserId(userId);
    }

    public boolean addGameToLibrary(int libraryId, Game game) {
        return libraryDAO.addGameToLibrary(libraryId, game);
    }

    public boolean removeGameFromLibrary(int libraryId, int gameId) {
        return libraryDAO.removeGameFromLibrary(libraryId, gameId);
    }

    public boolean updateGameState(int gameId, String newState) {
        return libraryDAO.updateGameState(gameId, newState);
    }
}





