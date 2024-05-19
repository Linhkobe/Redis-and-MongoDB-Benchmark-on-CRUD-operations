package com.example.bigdatareddismongodbfilm.services.mongodb;

import com.example.bigdatareddismongodbfilm.entity.Rating;
import com.example.bigdatareddismongodbfilm.repositories.mongodb.RatingRepository;
import com.example.bigdatareddismongodbfilm.services.redis.RatingRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
public class RatingServiceMongoImpl implements RatingServiceMongo {

    private static final Logger logger = LoggerFactory.getLogger(RatingServiceMongoImpl.class);

    @Autowired
    private Environment env;

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public List<Rating> findByUserIdWithUserAndMovieDetails(String userId) {
        return ratingRepository.findByUserIdWithUserAndMovieDetails(userId);
    }

    @Override
    public List<String> findUsersWhoRatedMovie(String movieId) {
        return ratingRepository.findUsersWhoRatedMovie(movieId);
    }

    @GetMapping("/user/{userId}")
    public List<Rating> getRatingsByUserId(@PathVariable String userId) {
        List<Rating> ratings = ratingRepository.findByUserIdWithUserAndMovieDetails(userId);
        System.out.println("Fetched ratings for user " + userId + ": " + ratings);
        return ratings;
    }

    @Override
    public List<Rating> getFirstNRatings(int n) {
        Pageable pageable = PageRequest.of(0, n);
        return ratingRepository.findAll(pageable).getContent();
    }

    public List<Rating> getRandomRatings(int count) {
        Pageable pageable = PageRequest.of(0, count);
        List<Rating> randomRatings = ratingRepository.findAll(pageable).getContent();
        return randomRatings;
    }


    @Autowired
    private RatingRedisService ratingRedisService;  // Autowired Redis Service

    @Override
    public Rating createRating(Rating rating) {
        Rating savedRating = ratingRepository.save(rating);
        return savedRating;
    }

    @Override
    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    @Override
    public Rating getRatingById(String id) {

        return ratingRepository.findById(id).orElse(null);
    }

    @Override
    public Rating updateRating(String id, Rating rating) {
        rating.setId(id);
        Rating updatedRating = ratingRepository.save(rating);
        ratingRedisService.saveRating(updatedRating);  // Update in Redis too
        return updatedRating;
    }

    @Override
    public void deleteRating(String id) {
        try {
            ratingRepository.deleteById(id);
            ratingRedisService.deleteRating(id);
            logger.info("Successfully deleted rating with id: {}", id);
        } catch (Exception e) {
            logger.error("Error occurred while deleting rating with id: {}", id, e);
        }
    }

    @Override
    public Page<Rating> getAllRatings(Pageable pageable) {
        return ratingRepository.findAll(pageable);
    }



    @EventListener(ContextRefreshedEvent.class)
    public void initRedisWithMongoData() {
        List<Rating> allRatings = getAllRatingsFromAtlas();
        allRatings.forEach(ratingRedisService::saveRating);
        System.out.println("Initialized Redis with existing MongoDB Atlas data(RATING collection).");
    }

    @EventListener(ContextRefreshedEvent.class)
    public void initLocalMongoWithAtlasData() {
        List<Rating> allRatings = getAllRatingsFromAtlas();
        allRatings.forEach(ratingRepository::save);
        System.out.println("Initialized local MongoDB with existing MongoDB Atlas data(RATING collection).");
    }

    public List<Rating> getAllRatingsFromAtlas() {
        try {
            String atlasUri = Objects.requireNonNull(env.getProperty("SPRING_DATA_MONGODB_ATLAS_URI"));
            System.out.println("MongoDB Atlas URI: " + atlasUri);

            MongoTemplate mongoTemplateAtlas = new MongoTemplate(new SimpleMongoClientDatabaseFactory(atlasUri));

            Query query = new Query();
            query.limit(1000);

            List<Rating> ratings = mongoTemplateAtlas.find(query, Rating.class);

            System.out.println("Number of ratings retrieved from MongoDB Atlas: " + ratings.size());
            return ratings;
        } catch (Exception e) {
            System.out.println("An error occurred while retrieving ratings from MongoDB Atlas:");
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Rating getRandomRating() {
        // Compter le nombre total de films dans la base de données
        long ratingCount = ratingRepository.count();
        if (ratingCount > 0) {
            // Générer un index aléatoire dans la plage de films disponibles
            Random random = new Random();
            int randomIndex = random.nextInt((int) ratingCount);

            // Obtenir une page contenant un film aléatoire
            Pageable pageable = PageRequest.of(randomIndex, 1);
            List<Rating> ratings = ratingRepository.findAll(pageable).getContent();

            // Renvoyer le premier film trouvé dans la page
            return ratings.get(0);
        } else {
            // Aucun film trouvé dans la base de données
            return null;
        }
    }
    public Rating updateRatingBenchmark(String id, Rating rating) {
        rating.setId(id);
        Rating updatedRating = ratingRepository.save(rating);
        return updatedRating;
    }
}
