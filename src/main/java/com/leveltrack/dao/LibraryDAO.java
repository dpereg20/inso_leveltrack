package com.leveltrack.dao;

import com.leveltrack.model.Game;

import java.util.List;

public interface LibraryDAO {
    List<Game> getGamesByUserId(int userId);
    boolean addGameToLibrary(int libraryId, int gameId, String state);
    boolean removeGameFromLibrary(int libraryId, int gameId);
    public boolean updateGameState(int gameId, int userId, String newState);
    boolean isGameInDatabase(String gameName);
    Game getGameByName(String gameName);
    int getLibraryIdByUserId(int userId);
    List<Game> getAllGames();
    List<Game> getGamesByGenreUser(int userId, String genre);
    List<Game> getGamesByGenre(String genre);
    boolean isGameInLibrary(int libraryId, int gameId);
    List<Game> searchGamesByGenre(String genre);
    int getGameScore(int gameId, int userId);
    boolean updateGameScore(int gameId, int userId, int score);

    List<String> getAllGenres();
}
