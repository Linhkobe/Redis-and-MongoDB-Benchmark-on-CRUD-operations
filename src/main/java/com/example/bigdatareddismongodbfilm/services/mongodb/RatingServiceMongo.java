// File: src/main/java/com/example/bigdatareddismongodbfilm/services/mongodb/RatingServiceMongo.java

package com.example.bigdatareddismongodbfilm.services.mongodb;

import com.example.bigdatareddismongodbfilm.entity.Movie;
import com.example.bigdatareddismongodbfilm.entity.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RatingServiceMongo {
    Rating createRating(Rating rating);
    List<Rating> getAllRatings();
    Rating getRatingById(String id);
    Rating updateRating(String id, Rating rating);
    void deleteRating(String id);
    Page<Rating> getAllRatings(Pageable pageable);
    Rating getRandomRating();
    Rating updateRatingBenchmark(String id, Rating rating);
}