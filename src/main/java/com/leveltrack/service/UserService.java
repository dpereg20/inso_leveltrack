package main.java.com.leveltrack.service;

import main.java.com.leveltrack.dao.UserDAO;
import main.java.com.leveltrack.dao.UserDAOImpl;
import main.java.com.leveltrack.model.UserBase;

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

}


