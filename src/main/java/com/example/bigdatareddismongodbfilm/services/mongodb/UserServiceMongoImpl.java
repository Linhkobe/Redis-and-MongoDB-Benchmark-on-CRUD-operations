package com.example.bigdatareddismongodbfilm.services.mongodb;

import com.example.bigdatareddismongodbfilm.entity.Movie;
import com.example.bigdatareddismongodbfilm.entity.Rating;
import com.example.bigdatareddismongodbfilm.entity.User;
import com.example.bigdatareddismongodbfilm.repositories.mongodb.UserRepository;
import com.example.bigdatareddismongodbfilm.services.redis.UserRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Service
public class UserServiceMongoImpl implements UserServiceMongo {
    @Autowired
    private Environment env;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getFirstNUsers(int n) {
        Pageable pageable = PageRequest.of(0, n);
        return userRepository.findAll(pageable).getContent();
    }
    public List<User> getRandomUsers(int count) {
        Pageable pageable = PageRequest.of(0, count, Sort.by("id").ascending());

        List<User> randomUsers = userRepository.findAll(pageable).getContent();

        return randomUsers;
    }

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

    @EventListener(ContextRefreshedEvent.class)
    public void initRedisWithMongoData() {
        List<User> allUsers = getAllUsersFromAtlas();
        allUsers.forEach(userRedisService::saveUser);
        System.out.println("Initialized Redis with existing MongoDB Atlas data(USER collection).");
    }

    @EventListener(ContextRefreshedEvent.class)
    public void initLocalMongoWithAtlasData() {
        List<User> allUsers = getAllUsersFromAtlas();
        allUsers.forEach(userRepository::save);
        System.out.println("Initialized local MongoDB with existing MongoDB Atlas data(USER collection).");
    }

    public List<User> getAllUsersFromAtlas() {
        try {
            String atlasUri = Objects.requireNonNull(env.getProperty("SPRING_DATA_MONGODB_ATLAS_URI"));
            System.out.println("MongoDB Atlas URI: " + atlasUri);

            MongoTemplate mongoTemplateAtlas = new MongoTemplate(new SimpleMongoClientDatabaseFactory(atlasUri));

            Query query = new Query();
            query.limit(1000);

            List<User> users = mongoTemplateAtlas.find(query, User.class);

            System.out.println("Number of users retrieved from MongoDB Atlas: " + users.size());
            return users;
        } catch (Exception e) {
            System.out.println("An error occurred while retrieving users from MongoDB Atlas:");
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public User getRandomUser() {
        // Compter le nombre total de films dans la base de données
        long userCount = userRepository.count();
        if (userCount > 0) {
            // Générer un index aléatoire dans la plage de films disponibles
            Random random = new Random();
            int randomIndex = random.nextInt((int) userCount);

            // Obtenir une page contenant un film aléatoire
            Pageable pageable = PageRequest.of(randomIndex, 1);
            List<User> users = userRepository.findAll(pageable).getContent();

            // Renvoyer le premier film trouvé dans la page
            return users.get(0);
        } else {
            // Aucun film trouvé dans la base de données
            return null;
        }
    }
    public User updateUserBenchmark(String id, User user) {
        user.setId(id);
        User updatedUser = userRepository.save(user);
        return updatedUser;
    }
}