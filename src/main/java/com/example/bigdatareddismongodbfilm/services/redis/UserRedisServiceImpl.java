package com.example.bigdatareddismongodbfilm.services.redis;

import com.example.bigdatareddismongodbfilm.entity.Movie;
import com.example.bigdatareddismongodbfilm.entity.Rating;
import com.example.bigdatareddismongodbfilm.entity.User;
import com.example.bigdatareddismongodbfilm.repositories.mongodb.UserRepository;
import com.example.bigdatareddismongodbfilm.repositories.redis.UserRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

// Annotation indiquant que cette classe est un service Spring
@Service
public class UserRedisServiceImpl implements UserRedisService {

    @Autowired
    private UserRepository userRepository;

    // Repository pour accéder aux données des utilisateurs dans Redis
    private final UserRedisRepository userRedisRepository;

    @Override
    public List<User> getFirstNUsers(int n) {
        Pageable pageable = PageRequest.of(0, n);
        return userRedisRepository.findAll(pageable).getContent();
    }

    public List<User> getRandomUsers(int count) {
        Pageable pageable = PageRequest.of(0, count, Sort.by("id").ascending());

        List<User> randomUsers = userRepository.findAll(pageable).getContent();

        return randomUsers;
    }

    // Injection de dépendance du repository via le constructeur
    @Autowired
    public UserRedisServiceImpl(UserRedisRepository userRedisRepository) {
        this.userRedisRepository = userRedisRepository;
    }
    @Override
    public User updateUser(String id, User user) {
        user.setId(id);
        return userRedisRepository.save(user);
    }
    // Méthode pour enregistrer un nouvel utilisateur dans Redis
    @Override
    public User saveUser(User user) {
        return userRedisRepository.save(user);
    }

    // Méthode pour trouver un utilisateur par son identifiant dans Redis
    @Override
    public Optional<User> findUserById(String id) {
        return userRedisRepository.findById(id);
    }

    // Méthode pour supprimer un utilisateur par son identifiant dans Redis
    @Override
    public void deleteUser(String id) {
        userRedisRepository.deleteById(id);
    }

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRedisRepository.findAll(pageable);
    }

    // Méthode pour récupérer tous les utilisateurs stockés dans Redis
    @Override
    public List<User> findAllUsers() {
        return userRedisRepository.findAll();
    }

    public User updateUserBenchmark(String id, User user) {
        user.setId(id);
        User updatedUser = userRedisRepository.save(user);
        return updatedUser;
    }
}