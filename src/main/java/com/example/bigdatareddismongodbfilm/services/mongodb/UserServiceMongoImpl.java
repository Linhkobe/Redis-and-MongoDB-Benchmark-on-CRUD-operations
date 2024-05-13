package com.example.bigdatareddismongodbfilm.services.mongodb;

import com.example.bigdatareddismongodbfilm.entity.User;
import com.example.bigdatareddismongodbfilm.repositories.mongodb.UserRepository;
import com.example.bigdatareddismongodbfilm.services.redis.UserRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceMongoImpl implements UserServiceMongo {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRedisService userRedisService;  // Autowired Redis Service

    @Override
    public User createUser(User user) {
        User savedUser = userRepository.save(user);
        return savedUser;
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
        User updatedUser = userRepository.save(user);
        userRedisService.saveUser(updatedUser);  // Update in Redis too
        return updatedUser;
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
        userRedisService.deleteUser(id);  // Delete from Redis as well
    }

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    // Method to initialize Redis with data from MongoDB at startup
    @EventListener(ContextRefreshedEvent.class)
    public void initRedisWithMongoData() {
        List<User> allUsers = getAllUsers();  // Retrieve all users from MongoDB
        allUsers.forEach(userRedisService::saveUser);  // Save each user to Redis
        System.out.println("Initialized Redis with existing MongoDB data(USER collection).");
    }
}