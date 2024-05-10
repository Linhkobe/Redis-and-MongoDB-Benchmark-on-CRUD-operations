package com.example.bigdatareddismongodbfilm.services.redis;

import com.example.bigdatareddismongodbfilm.entity.User;
import com.example.bigdatareddismongodbfilm.repositories.redis.UserRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Annotation indiquant que cette classe est un service Spring
@Service
public class UserRedisServiceImpl implements UserRedisService {

    // Repository pour accéder aux données des utilisateurs dans Redis
    private final UserRedisRepository userRedisRepository;

    // Injection de dépendance du repository via le constructeur
    @Autowired
    public UserRedisServiceImpl(UserRedisRepository userRedisRepository) {
        this.userRedisRepository = userRedisRepository;
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

    // Méthode pour récupérer tous les utilisateurs stockés dans Redis
    @Override
    public List<User> findAllUsers() {
        return userRedisRepository.findAll();
    }
}