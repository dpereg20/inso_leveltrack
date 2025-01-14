package com.leveltrack.dao;

import com.leveltrack.model.Game;

import java.util.List;

public interface LibraryDAO {
    List<Game> getGamesByUserId(int userId);
    boolean addGameToLibrary(int libraryId, int gameId, String state);
    boolean removeGameFromLibrary(int libraryId, int gameId);
     boolean updateGameState(int gameId, int userId, String newState);
    Game getGameByName(String gameName);
    int getLibraryIdByUserId(int userId);
    List<Game> getGamesByGenreUser(int userId, String genre);
    List<Game> getGamesByGenre(String genre);
    boolean isGameInLibrary(int libraryId, int gameId);
    int getGameScore(int gameId, int userId);
    boolean updateGameScore(int gameId, int userId, int score);
    List<String> getAllGenres();
}
