// File: src/main/java/com/example/bigdatareddismongodbfilm/repositories/mongodb/RatingRepository.java

package com.example.bigdatareddismongodbfilm.repositories.mongodb;

import com.example.bigdatareddismongodbfilm.entity.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RatingRepository extends MongoRepository<Rating, String> {
}