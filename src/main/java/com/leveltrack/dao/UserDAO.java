package com.leveltrack.dao;

import com.leveltrack.model.UserBase;
import java.util.List;

public interface UserDAO {
    UserBase findById(int id);
    List<UserBase> findAll();
    boolean insert(UserBase user);
    boolean update(UserBase user);
    boolean delete(int id);
    UserBase findByEmailAndPassword(String email, String password);
    boolean createUser(UserBase user);
    boolean updateProfile(UserBase user);

    boolean updateUserRole(int userId, String newRole);

    boolean emailExists(String email);
}

