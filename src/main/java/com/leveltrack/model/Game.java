package com.leveltrack.model;

public class Game {
    private int id;
    private String name;
    private String genre;
    private double price;
    private String state;
    private int score;

    public Game(int id, String name, String genre, double price, String state, int score) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.price = price;
        this.state = state;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public double getPrice() {
        return price;
    }

    public String getState() {
        return state;
    }

    public int getScore(){return score;}

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Genre: " + genre + ", Price: $" + price + ", State: " + state + ", Score: " + score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
