// UserRedisServiceImpl.java
package com.example.bigdatareddismongodbfilm.services.redis;

import com.example.bigdatareddismongodbfilm.entity.User;
import com.example.bigdatareddismongodbfilm.repositories.redis.UserRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserRedisServiceImpl implements UserRedisService {

    private final UserRedisRepository userRedisRepository;

    @Autowired
    public UserRedisServiceImpl(UserRedisRepository userRedisRepository) {
        this.userRedisRepository = userRedisRepository;
    }

    @Override
    public void deleteUser(String id) {
        userRedisRepository.deleteById(id);
    }

    @Override
    public List<User> findAllUsers() {
        return userRedisRepository.findAll();
    }
}