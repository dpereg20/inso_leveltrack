package main.java.com.leveltrack.service;

import main.java.com.leveltrack.dao.UserDAO;
import main.java.com.leveltrack.dao.UserDAOImpl;
import main.java.com.leveltrack.model.UserBase;

public class UserService {
    private final UserDAO userDAO;

    public UserService() throws Exception {
        this.userDAO = new UserDAOImpl();
        System.out.println("ahsds");
    }

    public UserBase authenticate(String email, String password) {
        return userDAO.findByEmailAndPassword(email, password);
    }
}


