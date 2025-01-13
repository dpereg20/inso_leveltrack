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

    public boolean isGameInLibrary(int userId, int gameId) {
        return libraryDAO.isGameInLibrary(userId, gameId);
    }

    public List<Game> getGamesByGenre(int userId, String genre){
        return libraryDAO.getGamesByGenre(userId, genre);
    }


    public boolean addGameToLibrary(int userId, int gameId, String state) {
        return libraryDAO.addGameToLibrary(userId, gameId, state);
    }


    public boolean removeGameFromLibrary(int libraryId, int gameId) {
        return libraryDAO.removeGameFromLibrary(libraryId, gameId);
    }

    public boolean updateGameState(int gameId, String newState) {
        return libraryDAO.updateGameState(gameId, newState);
    }

    public boolean isGameInDatabase(String gameName) {
        return libraryDAO.isGameInDatabase(gameName);
    }

    public Game getGameByName(String gameName) {
        return libraryDAO.getGameByName(gameName);
    }

    public int getLibraryIdByUserId(int userId) {
        return libraryDAO.getLibraryIdByUserId(userId);
    }


    public List<Game> getAllGamesFromDatabase() {
        return libraryDAO.getAllGames();
    }




}


