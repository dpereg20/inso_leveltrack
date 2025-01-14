package com.leveltrack.dao;

import com.leveltrack.model.UserBase;
import java.util.List;

public interface UserDAO {
    UserBase findById(int id);
    List<UserBase> findAll();
    boolean delete(int id);
    UserBase findByEmailAndPassword(String email, String password);
    boolean createUser(UserBase user);
    boolean updateProfiled(UserBase user);

    boolean updateUserRole(int userId, String newRole);
    boolean updateProfile(int id, String name, String email, String password);
    boolean emailExists(String email);
}