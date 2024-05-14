// UserRedisService.java
package com.example.bigdatareddismongodbfilm.services.redis;

import com.example.bigdatareddismongodbfilm.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UserRedisService {
    User saveUser(User user);
    Optional<User> findUserById(String id);
    void deleteUser(String id);
    List<User> findAllUsers();
    Page<User> getAllUsers(Pageable pageable);
}