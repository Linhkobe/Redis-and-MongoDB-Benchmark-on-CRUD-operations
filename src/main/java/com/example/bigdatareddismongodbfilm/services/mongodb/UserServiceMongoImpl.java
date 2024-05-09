package com.example.bigdatareddismongodbfilm.services.mongodb;

import com.example.bigdatareddismongodbfilm.entity.User;
import com.example.bigdatareddismongodbfilm.repositories.mongodb.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceMongoImpl implements UserServiceMongo {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User updateUser(String id, User user) {
        user.setId(id);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}