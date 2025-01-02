package dao;

import model.UserBase;
import java.util.List;

public interface UserDAO {
    UserBase findById(int id);
    List<UserBase> findAll();
    boolean insert(UserBase user);
    boolean update(UserBase user);
    boolean delete(int id);
}

