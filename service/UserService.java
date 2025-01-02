package service;

import dao.UserDAO;
import dao.UserDAOImpl;
import model.UserBase;

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


