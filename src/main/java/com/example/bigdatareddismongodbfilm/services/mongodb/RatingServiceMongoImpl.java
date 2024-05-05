// File: src/main/java/com/example/bigdatareddismongodbfilm/services/mongodb/RatingServiceMongoImpl.java

package com.example.bigdatareddismongodbfilm.services.mongodb;

import com.example.bigdatareddismongodbfilm.entity.Rating;
import com.example.bigdatareddismongodbfilm.repositories.mongodb.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceMongoImpl implements RatingServiceMongo {

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public Rating createRating(Rating rating) {
        return ratingRepository.save(rating);
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
        return ratingRepository.save(rating);
    }

    @Override
    public void deleteRating(String id) {
        ratingRepository.deleteById(id);
    }
}