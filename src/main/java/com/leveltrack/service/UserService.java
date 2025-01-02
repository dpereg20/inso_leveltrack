package com.leveltrack.service;

import com.leveltrack.dao.UserDAO;
import com.leveltrack.dao.UserDAOImpl;
import com.leveltrack.model.UserBase;

public class UserService {
    private final UserDAO userDAO;

    public UserService() throws Exception {
        this.userDAO = new UserDAOImpl();
    }

    public UserBase authenticate(String email, String password) {
        return userDAO.findByEmailAndPassword(email, password);
    }

    public boolean registerUser(String name, String email, String password, String role) {
        // Validaciones básicas
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || role.isEmpty()) {
            throw new IllegalArgumentException("Todos los campos son obligatorios.");
        }
        // Crea el usuario
        UserBase user = new UserBase(0, name, email, role);
        user.setPassword(password); // Establece la contraseña antes de guardarla
        return userDAO.createUser(user);
    }

    public boolean updateUserProfile(int id, String name, String email, String password) {
        // Validaciones básicas
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("Todos los campos son obligatorios.");
        }
        UserBase user = new UserBase(id, name, email, null);
        user.setPassword(password); // Establece la contraseña
        return userDAO.updateProfile(user);
    }

}


