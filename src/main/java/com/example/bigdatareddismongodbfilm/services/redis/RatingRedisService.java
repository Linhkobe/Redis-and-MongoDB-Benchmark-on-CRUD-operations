package com.example.bigdatareddismongodbfilm.services.redis;

import com.example.bigdatareddismongodbfilm.entity.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RatingRedisService {

    void deleteRating(String id);
    List<Rating> findAllRatings();
    Optional<Rating> findRatingById(String id);
    Rating saveRating(Rating rating);
    Page<Rating> getAllRatings(Pageable pageable);
}