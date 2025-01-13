package com.leveltrack.dao;

import com.leveltrack.model.Game;

import java.util.List;
import java.util.Map;

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

    boolean updateScore(int userId, int gameId, int score);
    int getGameScore(int userId, int gameId);
    double getAvgGameScore(int gameId);

    List<String> getAllGenres();

    Map<Integer, Double> getAverageScores();
}
