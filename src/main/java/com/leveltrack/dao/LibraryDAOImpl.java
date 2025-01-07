package com.leveltrack.dao;

import com.leveltrack.model.Game;
import com.leveltrack.util.DatabaseConnection;
import com.leveltrack.util.QueryLoader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibraryDAOImpl implements LibraryDAO {
    private final Connection connection;

    public LibraryDAOImpl() throws Exception {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public List<Game> getGamesByUserId(int userId) {
        List<Game> games = new ArrayList<>();
        String query = QueryLoader.getQuery("library.getGamesByUserId");
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                games.add(new Game(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("genre"),
                        rs.getDouble("price"),
                        rs.getString("state")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return games;
    }

    @Override
    public boolean addGameToLibrary(int libraryId, Game game) {
        String query = QueryLoader.getQuery("library.addGame");
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, libraryId);
            stmt.setString(2, game.getName());
            stmt.setString(3, game.getGenre());
            stmt.setDouble(4, game.getPrice());
            stmt.setString(5, game.getState());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeGameFromLibrary(int libraryId, int gameId) {
        String query = QueryLoader.getQuery("library.removeGame");
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, libraryId);
            stmt.setInt(2, gameId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateGameState(int gameId, String newState) {
        String query = QueryLoader.getQuery("library.updateGameState");
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newState);
            stmt.setInt(2, gameId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}