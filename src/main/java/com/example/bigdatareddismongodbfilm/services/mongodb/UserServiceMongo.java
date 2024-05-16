package com.example.bigdatareddismongodbfilm.services.mongodb;
import com.example.bigdatareddismongodbfilm.entity.Rating;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import com.example.bigdatareddismongodbfilm.entity.User;

import java.util.List;

public interface UserServiceMongo {
    User createUser(User user);
    List<User> getAllUsers();
    User getUserById(String id);
    User updateUser(String id, User user);
    void deleteUser(String id);
    Page<User> getAllUsers(Pageable pageable);
    User getRandomUser();
    User updateUserBenchmark(String id, User user);
}