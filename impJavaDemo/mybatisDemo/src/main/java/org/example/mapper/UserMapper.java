package org.example.mapper;
import org.example.User;

public interface UserMapper {
    User selectUserById(int id);
    void insertUser(User user);
}
