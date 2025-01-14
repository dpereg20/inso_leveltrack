package com.leveltrack.model;

public class Game {
    private int id;
    private String name;
    private String genre;
    private double price;
    private String state;
    private int score;

    /**
     * Constructor for the Game class.
     *
     * @param id    The unique ID of the game.
     * @param name  The name of the game.
     * @param genre The genre of the game.
     * @param price The price of the game.
     * @param state The current state of the game (e.g., "Available", "Played").
     * @param score The user-assigned score for the game.
     */
    public Game(int id, String name, String genre, double price, String state, int score) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.price = price;
        this.state = state;
        this.score = score;
    }

    /**
     * Retrieves the unique ID of the game.
     *
     * @return The game ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the name of the game.
     *
     * @return The name of the game.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the genre of the game.
     *
     * @return The genre of the game.
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Retrieves the price of the game.
     *
     * @return The price of the game.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Retrieves the current state of the game.
     *
     * @return The state of the game (e.g., "Available", "Played"...).
     */
    public String getState() {
        return state;
    }

    /**
     * Retrieves the user-assigned score of the game.
     *
     * @return The score of the game.
     */
    public int getScore(){
        return score;
    }

    /**
     * Provides a string representation of the game.
     *
     * @return A formatted string containing the game's details.
     *
     */
    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Genre: " + genre + ", Price: $" + price + ", State: " + state + ", Score: " + score;
    }

    /**
     * Sets a new name for the game.
     *
     * @param name The new name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets a new genre for the game.
     *
     * @param genre The new genre to set.
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Sets a new price for the game.
     *
     * @param price The new price to set.
     */
    public void setPrice(double price) {
        this.price = price;
    }
}
