package com.leveltrack.controller;

import com.leveltrack.model.Game;
import com.leveltrack.service.LibraryService;

import java.util.List;
import java.util.Map;

public class LibraryController {
    private final LibraryService libraryService;

    public LibraryController() throws Exception {
        this.libraryService = new LibraryService();
    }

    public List<Game> getGamesByUserId(int userId) {
        return libraryService.getGamesByUserId(userId);
    }

    public List<Game> getGamesByGenreUser(int userId, String genre) {
        return libraryService.getGamesByGenreUser(userId, genre);
    }

    public boolean addGameToLibrary(int userId, String gameName) throws Exception {
        Game game = libraryService.getGameByName(gameName);
        if (game == null) {
            throw new Exception("Game not found in the database.");
        }
        boolean alreadyInLibrary = libraryService.isGameInLibrary(userId, game.getId());
        if (alreadyInLibrary) {
            return false;
        }
        return libraryService.addGameToLibrary(userId, game.getId(), "Available");
    }

    public boolean removeGame(int userId, int gameId) {
        int libraryId = libraryService.getLibraryIdByUserId(userId);
        return libraryService.removeGameFromLibrary(libraryId, gameId);
    }

    public boolean changeGameState(int userId, int gameId, String newState) {
        return libraryService.updateGameState(userId, gameId, newState);
    }

    public List<Game> getAvailableGames() {
        return libraryService.getAllGamesFromDatabase();
    }

    public List<Game> getAllGames() {
        return libraryService.getAllGames();
    }

    public List<Game> searchGamesByName(String keyword) {
        return libraryService.searchGamesByName(keyword);
    }

    public List<Game> searchGamesByGenre(String genre) {
        return libraryService.searchGamesByGenre(genre);
    }

    public List<String> getAllGenres() {
        return libraryService.getAllGenres();
    }

    public boolean updateGameState(int gameId, int userId, String newState) {
        return libraryService.updateGameState(gameId, userId, newState);
    }

    // Nuevo método para actualizar la puntuación del juego
    public boolean updateGameScore(int gameId, int userId, int score) {
        System.out.println("Updating score for gameId: " + gameId + ", userId: " + userId + " to " + score);
        return libraryService.updateGameScore(gameId, userId, score);
    }

    // Nuevo método para obtener la puntuación de un juego
    public int getGameScore(int gameId, int userId) {
        return libraryService.getGameScore(gameId, userId);
    }

    public Map<Integer, Double> getAverageScores() {
        return libraryService.getAverageScores();
    }
}
