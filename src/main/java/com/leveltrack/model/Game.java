package com.leveltrack.model;

public class Game {
    private int id;
    private String name;
    private String genre;
    private double price;
    private String state;

    public Game(int id, String name, String genre, double price, String state) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.price = price;
        this.state = state;
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

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Genre: " + genre + ", Price: $" + price + ", State: " + state;
    }
}
