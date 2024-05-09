package com.example.bigdatareddismongodbfilm.services.mongodb;

import com.example.bigdatareddismongodbfilm.entity.User;

import java.util.List;

public interface UserServiceMongo {
    User createUser(User user);
    List<User> getAllUsers();
    User getUserById(String id);
    User updateUser(String id, User user);
    void deleteUser(String id);
}