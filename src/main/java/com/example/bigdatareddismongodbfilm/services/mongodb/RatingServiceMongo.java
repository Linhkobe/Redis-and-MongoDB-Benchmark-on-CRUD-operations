// File: src/main/java/com/example/bigdatareddismongodbfilm/services/mongodb/RatingServiceMongo.java

package com.example.bigdatareddismongodbfilm.services.mongodb;

import com.example.bigdatareddismongodbfilm.entity.Rating;

import java.util.List;

public interface RatingServiceMongo {
    Rating createRating(Rating rating);
    List<Rating> getAllRatings();
    Rating getRatingById(String id);
    Rating updateRating(String id, Rating rating);
    void deleteRating(String id);
}