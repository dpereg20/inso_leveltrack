package com.leveltrack.service;

import com.leveltrack.dao.UserDAO;
import com.leveltrack.dao.UserDAOImpl;
import com.leveltrack.model.Administrator;
import com.leveltrack.model.Moderator;
import com.leveltrack.model.Regular_User;
import com.leveltrack.model.UserBase;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class UserService {
    private final UserDAO userDAO;

    public UserService() throws Exception {
        this.userDAO = new UserDAOImpl();
    }

    /**
     * Authenticates a user based on email and password.
     * @param email The user's email.
     * @param password The user's password.
     * @return The authenticated UserBase object or null if authentication fails.
     */
    public UserBase authenticate(String email, String password) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Email and password are required.");
        }

        UserBase user = userDAO.findByEmailAndPassword(email, password);
        if (user == null) {
            return null;
        }

        return createUserInstance(user.getRole(), user);
    }

    /**
     * Registers a new user in the system.
     * @param name The user's name.
     * @param email The user's email.
     * @param password The user's password.
     * @param role The user's role (Administrator, Moderator, Regular_User).
     * @return True if the user was successfully registered, false otherwise.
     */
    public boolean registerUser(String name, String email, String password, String role) {
        if (name == null || name.isEmpty() || email == null || email.isEmpty() || password == null || password.isEmpty() || role == null || role.isEmpty()) {
            throw new IllegalArgumentException("All fields are required.");
        }

        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format.");
        }

        if (!isValidRole(role)) {
            throw new IllegalArgumentException("Invalid role. Accepted roles: Administrator, Moderator, Regular_User.");
        }

        UserBase user = createUserInstance(role, 0, name, email, password);
        return userDAO.createUser(user);
    }

    /**
     * Updates a user's profile.
     * @param id The user's ID.
     * @param name The updated name.
     * @param email The updated email.
     * @param password The updated password.
     * @return True if the profile was successfully updated, false otherwise.
     */
    public boolean updateUserProfile(int id, String name, String email, String password) {
        if (id <= 0 || name == null || name.isEmpty() || email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("All fields are required, and ID must be positive.");
        }

        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format.");
        }

        UserBase user = userDAO.findById(id);
        if (user == null) {
            throw new IllegalArgumentException("User not found.");
        }

        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        return userDAO.updateProfiled(user);
    }

    /**
     * Helper method to validate the email format.
     * @param email The email to validate.
     * @return True if the email format is valid, false otherwise.
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }

    /**
     * Helper method to validate the role input.
     * @param userId The role to validate.
     * @return True if the role is valid, false otherwise.
     */
    public boolean isAdmin(int userId) {
        UserBase user = userDAO.findById(userId);
        return user != null && "ADMINISTRATOR".equalsIgnoreCase(user.getRole());
    }

    public boolean updateUserRole(int userId, String newRole) {
        if (!isValidRole(newRole)) {
            throw new IllegalArgumentException("Invalid role: " + newRole);
        }
        return userDAO.updateUserRole(userId, newRole);
    }

    public boolean partialUpdateUserProfile(int id, String name, String email, String password) {
        UserBase existingUser = userDAO.findById(id);

        if (existingUser == null) {
            throw new IllegalArgumentException("User not found.");
        }

        String updatedName = (name == null || name.isEmpty()) ? existingUser.getName() : name;
        String updatedEmail = (email == null || email.isEmpty()) ? existingUser.getEmail() : email;
        String updatedPassword = (password == null || password.isEmpty()) ? existingUser.getPassword() : hashPassword(password);

        return userDAO.updateProfile(id, updatedName, updatedEmail, updatedPassword);
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al inicializar el algoritmo de hash", e);
        }
    }


    public boolean isValidRole(String role) {
        return role.equalsIgnoreCase("ADMINISTRATOR") ||
                role.equalsIgnoreCase("MODERATOR") ||
                role.equalsIgnoreCase("REGULAR_USER");
    }


    /**
     * Helper method to create an instance of a UserBase subclass based on role.
     * @param role The user's role.
     * @param user A UserBase object with basic data (for database retrieval).
     * @return A subclass instance of UserBase.
     */
    private UserBase createUserInstance(String role, UserBase user) {
        return switch (role.toUpperCase()) {
            case "ADMINISTRATOR" -> new Administrator(user.getId(), user.getName(), user.getEmail());
            case "MODERATOR" -> new Moderator(user.getId(), user.getName(), user.getEmail());
            default -> new Regular_User(user.getId(), user.getName(), user.getEmail());
        };
    }

    /**
     * Overloaded helper method to create a new user instance from scratch.
     * @param role The user's role.
     * @param id The user's ID.
     * @param name The user's name.
     * @param email The user's email.
     * @param password The user's password.
     * @return A subclass instance of UserBase.
     */
    private UserBase createUserInstance(String role, int id, String name, String email, String password) {
        UserBase user;


        switch (role) {
            case "ADMINISTRATOR":
                user = new Administrator(id, name, email);
                break;
            case "MODERATOR":
                user = new Moderator(id, name, email);
                break;
            default:
                user = new Regular_User(id, name, email);
                break;
        }


        user.setPassword(password);
        return user;
    }

    public boolean emailExists(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email is required.");
        }
        return userDAO.emailExists(email);
    }

    public List<UserBase> getAllUsers() {
        return userDAO.findAll();
    }

    public boolean deleteUser(int userId){
        return userDAO.delete(userId);
    }
}