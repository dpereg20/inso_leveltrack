package com.leveltrack.model;

import java.util.List;

public class Library {
    private int id;
    private List<Game> games;

    public Library(int id, List<Game> games) {
        this.id = id;
        this.games = games;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public void addGame(Game game) {
        this.games.add(game);
    }

    public void removeGame(Game game) {
        this.games.remove(game);
    }
}


