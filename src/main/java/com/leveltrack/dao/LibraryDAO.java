package com.leveltrack.dao;

import com.leveltrack.model.Game;

import java.util.List;

public interface LibraryDAO {
    List<Game> getGamesByUserId(int userId);
    boolean addGameToLibrary(int libraryId, Game game);
    boolean removeGameFromLibrary(int libraryId, int gameId);
    boolean updateGameState(int gameId, String newState);
}


