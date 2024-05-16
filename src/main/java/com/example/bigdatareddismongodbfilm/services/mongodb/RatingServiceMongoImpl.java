package com.example.bigdatareddismongodbfilm.services.mongodb;

import com.example.bigdatareddismongodbfilm.entity.Movie;
import com.example.bigdatareddismongodbfilm.entity.Rating;
import com.example.bigdatareddismongodbfilm.repositories.mongodb.RatingRepository;
import com.example.bigdatareddismongodbfilm.services.redis.RatingRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;

@Service
public class RatingServiceMongoImpl implements RatingServiceMongo {

    private static final Logger logger = LoggerFactory.getLogger(RatingServiceMongoImpl.class);

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public List<Rating> getFirstNRatings(int n) {
        Pageable pageable = PageRequest.of(0, n);
        return ratingRepository.findAll(pageable).getContent();
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


    // Method to initialize Redis with data from MongoDB at startup
    @EventListener(ContextRefreshedEvent.class)
    public void initRedisWithMongoData() {
        List<Rating> allRatings = getAllRatings();  // Retrieve all ratings from MongoDB
        allRatings.forEach(ratingRedisService::saveRating);  // Save each rating to Redis
        System.out.println("Initialized Redis with existing MongoDB data(RATING collection).");
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
