package com.example.bigdatareddismongodbfilm.services.redis;

import com.example.bigdatareddismongodbfilm.entity.Rating;

import java.util.List;
import java.util.Optional;

public interface RatingRedisService {
    Rating saveRating(Rating rating);
    Optional<Rating> findRatingById(String id);
    void deleteRating(String id);
    List<Rating> findAllRatings();

}