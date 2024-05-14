package com.example.bigdatareddismongodbfilm.services.mongodb;

import com.example.bigdatareddismongodbfilm.entity.Rating;
import com.example.bigdatareddismongodbfilm.repositories.mongodb.RatingRepository;
import com.example.bigdatareddismongodbfilm.services.redis.RatingRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.List;

@Service
public class RatingServiceMongoImpl implements RatingServiceMongo {

    @Autowired
    private RatingRepository ratingRepository;

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
        ratingRepository.deleteById(id);
        ratingRedisService.deleteRating(id);
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
}
