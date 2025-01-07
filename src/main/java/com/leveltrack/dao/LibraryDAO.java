package com.leveltrack.dao;

import com.leveltrack.model.Game;

import java.util.List;

public interface LibraryDAO {
    List<Game> getAllGames(); // Fetch all games from the database
    List<Game> getGamesByGenre(String genre); // Fetch games filtered by genre
    List<Game> getUserLibraryGames(int userId); // Fetch games in the user's library
    boolean isGameInLibrary(int userId, int gameId); // Check if a game is in the user's library
    boolean addGameToLibrary(int userId, int gameId); // Add a game to the user's library
}


