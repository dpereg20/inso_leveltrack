package com.leveltrack.dao;

import com.leveltrack.model.Game;

import java.util.List;

public interface LibraryDAO {
    List<Game> getGamesByUserId(int userId);
    boolean addGameToLibrary(int libraryId, int gameId, String state);
    boolean removeGameFromLibrary(int libraryId, int gameId);
    boolean updateGameState(int gameId, String newState);
    boolean isGameInDatabase(String gameName);
    Game getGameByName(String gameName);
    int getLibraryIdByUserId(int userId);
    List<Game> getAllGames();
    List<Game> getGamesByGenre(int userId, String genre);

    boolean isGameInLibrary(int libraryId, int gameId);

}
