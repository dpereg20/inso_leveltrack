package com.leveltrack.dao;

import com.leveltrack.model.Game;

import java.util.List;

public interface GameDAO {
    boolean saveGame(Game game);
    Game findGameBySteamAppId(int steamAppId);
    List<Game> findGamesByGenre(String genre);
    Game findGameByName(String name);
    public boolean isGameInDatabase(String gameName);

    boolean addGame(Game game);
    boolean updateGame(Game game);
    boolean deleteGame(int gameId);

    List<Game> getAllGames();
}

