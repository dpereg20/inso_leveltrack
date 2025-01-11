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

    public boolean addGame(Game game) {
        return gameDAO.addGame(game);
    }

    public boolean updateGame(Game game) {
        return gameDAO.updateGame(game);
    }

    public boolean deleteGame(int gameId) {
        return gameDAO.deleteGame(gameId);
    }

    public List<Game> getAllGames() {
        try {
            return gameDAO.getAllGames();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
