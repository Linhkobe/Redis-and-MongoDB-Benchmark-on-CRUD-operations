// File: src/main/java/com/example/bigdatareddismongodbfilm/repositories/mongodb/MovieRepository.java

package com.example.bigdatareddismongodbfilm.repositories.mongodb;

import com.example.bigdatareddismongodbfilm.entity.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieRepository extends MongoRepository<Movie, String> {
}