package com.example.bigdatareddismongodbfilm.controllers.redis;

import com.example.bigdatareddismongodbfilm.entity.User;
import com.example.bigdatareddismongodbfilm.services.redis.UserRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/redis/users")
public class UserRedisController {

    @Autowired
    private UserRedisService userRedisService;

    // Endpoint pour créer un nouvel utilisateur
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userRedisService.saveUser(user));
    }

    // Endpoint pour récupérer un utilisateur par son identifiant
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        Optional<User> user = userRedisService.findUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint pour mettre à jour un utilisateur existant
    @PutMapping("update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User newUser) {
        // Puisque Redis n'a pas de fonctionnalité de mise à jour, nous sauvegardons simplement le nouvel utilisateur
        User updatedUser = userRedisService.updateUser(id,newUser);
        return ResponseEntity.ok(updatedUser);
    }

    // Endpoint pour supprimer un utilisateur par son identifiant
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userRedisService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint pour récupérer tous les utilisateurs
    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRedisService.findAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }
    @GetMapping("/paged")
    public Page <User> getAllUsersPaged(Pageable pageable) {
        return userRedisService.getAllUsers(pageable);
    }
}
