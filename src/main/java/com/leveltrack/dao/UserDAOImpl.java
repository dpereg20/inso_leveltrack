package com.leveltrack.dao;

import com.leveltrack.model.Administrator;
import com.leveltrack.model.Moderator;
import com.leveltrack.model.Regular_User;
import com.leveltrack.model.UserBase;
import com.leveltrack.util.DatabaseConnection;
import com.leveltrack.util.QueryLoader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private final Connection connection;

    public UserDAOImpl() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public UserBase findByEmailAndPassword(String email, String password) {
        UserBase user = null;
        String query = QueryLoader.getQuery("user.findByEmailAndPassword");
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String role = rs.getString("role");
                user = createUserInstance(
                        role,
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    private UserBase createUserInstance(String role, int id, String name, String email) {
        switch (role) {
            case "Administrator":
                return new Administrator(id, name, email);
            case "Moderator":
                return new Moderator(id, name, email);
            default:
                return new Regular_User(id, name, email);
        }
    }


    @Override
    public UserBase findById(int id) {
        String query = QueryLoader.getQuery("user.findById");
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String role = rs.getString("role");
                return createUserInstance(
                        role,
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private UserBase createUserInstance(String role, int id, String name, String email, String password) {
        UserBase user;
        switch (role) {
            case "Administrator":
                user = new Administrator(id, name, email);
                break;
            case "Moderator":
                user = new Moderator(id, name, email);
                break;
            case "Regular_User":
            default:
                user = new Regular_User(id, name, email);
                break;
        }
        user.setPassword(password);
        return user;
    }



    @Override
    public List<UserBase> findAll() {
        List<UserBase> users = new ArrayList<>();
        String query = QueryLoader.getQuery("user.findAll");
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String role = rs.getString("role");
                users.add(createUserInstance(
                        role,
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }


    @Override
    public boolean insert(UserBase user) {
        String query = QueryLoader.getQuery("user.insert");
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRole());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(UserBase user) {
        String query = QueryLoader.getQuery("user.update");
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRole());
            stmt.setInt(5, user.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String query = QueryLoader.getQuery("user.delete");
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean createUser(UserBase user) {
        String query = QueryLoader.getQuery("user.insert");
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRole());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateProfiled(UserBase user) {
        String query = QueryLoader.getQuery("user.updateProfile");
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setInt(4, user.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateProfile(int id, String name, String email, String password) {
        String query = QueryLoader.getQuery("user.updateProfile");
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setInt(4, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    @Override
    public boolean updateUserRole(int userId, String newRole) {
        String query = QueryLoader.getQuery("user.updateRole");
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newRole);
            stmt.setInt(2, userId);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean emailExists(String email) {
        String query = QueryLoader.getQuery("user.emailExists");
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Returns true if a row is found
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
