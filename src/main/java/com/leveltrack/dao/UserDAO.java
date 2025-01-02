package main.java.com.leveltrack.dao;

import main.java.com.leveltrack.model.UserBase;
import java.util.List;

public interface UserDAO {
    UserBase findById(int id);
    List<UserBase> findAll();
    boolean insert(UserBase user);
    boolean update(UserBase user);
    boolean delete(int id);
    UserBase findByEmailAndPassword(String email, String password);
}

