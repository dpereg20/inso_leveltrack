package com.leveltrack.service;

import com.leveltrack.dao.GameDAO;
import com.leveltrack.dao.GameDAOImpl;
import com.leveltrack.model.Game;
import com.leveltrack.util.GameAPIClient;

import org.json.JSONObject;

import java.util.List;
import java.util.ResourceBundle;

public class GameService {
    private final GameDAO gameDAO;

    public GameService() throws Exception {
        this.gameDAO = new GameDAOImpl();
    }

    public Game fetchAndSaveGameFromSteam(int steamAppId) throws Exception {
        String jsonResponse = GameAPIClient.fetchGameDetailsFromSteam(steamAppId);
        JSONObject json = new JSONObject(jsonResponse);

        if (json.has(String.valueOf(steamAppId)) && json.getJSONObject(String.valueOf(steamAppId)).getBoolean("success")) {
            JSONObject data = json.getJSONObject(String.valueOf(steamAppId)).getJSONObject("data");
            String name = data.getString("name");
            String genre = data.getJSONArray("genres").getJSONObject(0).getString("description");
            double price = data.optDouble("price_overview.final", 0) / 100.0;

            ResourceBundle rs = null;
            Game game = new Game(0, name, genre, price, rs.getString("state"));
            gameDAO.saveGame(game);
            return game;
        } else {
            throw new Exception("El juego no existe en Steam.");
        }
    }

    public List<Game> findGamesByGenre(String genre) {
        return gameDAO.findGamesByGenre(genre);
    }



}
